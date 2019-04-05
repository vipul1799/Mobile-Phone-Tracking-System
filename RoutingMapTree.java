public class RoutingMapTree{
	Exchange root;
	public RoutingMapTree(){
		root = new Exchange(0);
	}

	public RoutingMapTree(Exchange a){
		root = a;
	}

	public Boolean containsNode(Exchange a){		//checks if the tree contains that node
		if(a.equals(root))
			return true;				//pre traversal
		Boolean ans = false;		
		for(int i=1;i<=root.numChildren();i++)
		{
			ans = ans || root.subtree(i).containsNode(a);
		}

		return ans;

	}

	public void switchOn(MobilePhone a,Exchange b) throws ObjectException{ //presence of b is handled in perfAction
		
		


		if(b.isExternal()==false)			// if exchange is not a leaf
			throw new ObjectException("The base is not a level-0 station");

		if(containsNode(b)==false)
			return;

		if( root.residentSet().IsMember(a)==true)			//if mobile already present
			{
				if(a.status()==false)
					{
						a.switchOn();
						return;
					}
				else
					throw new ObjectException("MobilePhone with ID "+a.number()+" is already on");
			} 

		if(containsNode(b)==true && root.residentSet().IsMember(a)==false){				
			a.switchOn();			
			root.residentSet().Insert(a);					//inserts in root set
			for(int i=1;i<=root.numChildren();i++)		//then traverses to the level0 exchange recursively
			{
				root.subtree(i).switchOn(a,b);
			}
		return;
		}

	}
	public void switchOff(MobilePhone a) throws ObjectException{		
			
			if(a.status())
				a.switchOff();
			else
				throw new ObjectException("Error - mobile with "+a.number()+ " is already switched off");

			
			return;

			
		
	}

	public Boolean hasInt(int a){		//checks if the tree has Exchange with id
		Boolean flag = false;
		if(root.getKey()==a)
		{	
			flag=true;
			return flag;
		}					//if root of subtree has that id
		if(root.numChildren()==0)
			return false;

		for(int i=1;i<=root.numChildren();i++){
				
			flag = root.subtree(i).hasInt(a) || flag;	
		
			if(flag)			//if found in a subtree than break the loop and return true
				break;

		}

		return flag;


	}

	public Exchange findInt(int a) throws ObjectException{		//gives reference of exchange of specific id is present
		Exchange ans=null;

		if(this.hasInt(a)==false)				//hasint checks if an exchange with a is present
			throw new ObjectException("Error - No exchange with identifier "+a+" found in the network");

		if(root.getKey()==a)
		{	
			return root;		
		}
		for(int i=1;i<=root.numChildren();i++)
		{
			if(root.subtree(i).hasInt(a))		/*only subtree having exc with id is entered*/
				{ans= root.subtree(i).findInt(a);		/*once if found further tree is not checked*/
					break;}
		}



		return ans;


	}

	public void delPho(MobilePhone a){

		if(root.residentSet().IsMember(a)==false)		//for only traversing into branches with mobile
			return;		
			
		try
		{
			if(root.residentSet().IsMember(a)==true)
			{	
				root.residentSet().Delete(a);
				for(int i=1;i<=root.numChildren();i++)
				{
					root.subtree(i).delPho(a);		//checks all the branches
				}
			}
		}

			catch(ObjectException e){}
			return;
		
	}

	public Exchange findPhone(MobilePhone m) throws ObjectException
	{
		if(this.root.residentSet().IsMember(m))
			{
				if(m.status())
					return m.location();
				else
					throw new ObjectException("Error - Mobile phone with identifier "+m.number()+" is currently switched off");
			}
		else
			throw new ObjectException("Error - No mobile phone with identifier "+m.number()+" found in the network");
	}

	public Exchange lowestRouter(Exchange a, Exchange b)
	{
		if(a.equals(b))
			return a;
		Exchange c=null,d=null;
		try{
			c=a.parent();
			d=b.parent();
		}
		catch(ObjectException e){}

		return lowestRouter(c,d);

	}

	public ExchangeList routeCall(MobilePhone a, MobilePhone b) throws ObjectException
	{
		ExchangeList ans = new ExchangeList();
		ExchangeList broute = new ExchangeList();
		Exchange temp =null,temp2=null,common=null;

		if(this.root.residentSet().IsMember(a))
			{
				if(a.status()==false)
					throw new ObjectException("Error - Mobile phone with identifier "+a.number()+" is currently switched off");
			}
		else
			throw new ObjectException("Error - No mobile phone with identifier "+a.number()+" found in the network");

		if(this.root.residentSet().IsMember(b))
			{
				if(b.status()==false)
					throw new ObjectException("Error - Mobile phone with identifier "+b.number()+" is currently switched off");
			}
		else
			throw new ObjectException("Error - No mobile phone with identifier "+b.number()+" found in the network");


		try
		{
			temp=a.location();
			temp2=b.location();
			common=lowestRouter(temp,temp2);
			while(temp.equals(common)==false)
			{
				ans.addLast(temp);
				temp = temp.parent();
			}

			 ans.addLast(temp);

			while(temp2.equals(common)==false)
			{
				broute.addFirst(temp2);
				temp2 = temp2.parent();
			}

			Node<Exchange> alast = ans.getTail();
			Node<Exchange> bfirst = broute.getHead();

			alast.setNext(bfirst); 


		}
		catch(ObjectException e){}

		return ans;
	}

	public void movePhone(MobilePhone a, Exchange b)
	{
		this.delPho(a);

		a.changeLoc(b);

		try
		{
			this.switchOn(a,b);
		}
		catch(ObjectException e){}
		return;
	}

	
	public String performAction(String actionMessage)
	{		
		

		String str[] = actionMessage.split(" ");

		if(str[0].equals("addExchange")){
			int a = Integer.parseInt(str[1]);
			int b = Integer.parseInt(str[2]);
			Exchange temp=null;
			try{
					temp = findInt(a);			//finds exchange with specified key
			}
			catch(ObjectException e){
				return actionMessage+": " + e.getMessage();
			}

			Exchange temp2 = new Exchange(b,temp);		//creates new exchange to be added

			temp.chList().addLast(temp2);			//adds it to the last
		
			return "";

		}
		

		else if(str[0].equals("switchOnMobile")){
			
			int a = Integer.parseInt(str[1]);
			int b = Integer.parseInt(str[2]);

			Exchange temp=null;MobilePhone temp2 = null;
			try
			{
					temp = findInt(b);		//finds exchange with specified key
			}
			catch(ObjectException e)
			{
					return actionMessage+": " + e.getMessage();
			}

			if(this.root.residentSet().hasMob(a))
			{
				try
				{
					temp2=this.root.residentSet().findMob(a);
					if(temp2.status()==false)
						{
							temp2.switchOn();
							return "";
						}

				}
				catch(ObjectException e){}
			}



			


			temp2 = new MobilePhone(a,temp);		//creates phone
			temp2.switchOn();

			try{this.switchOn(temp2,temp);}			//uses tree method req phone as input
			catch(ObjectException e){
				return actionMessage+": " + e.getMessage();
			}

			return "";

		}

		else if(str[0].equals("switchOffMobile")){
			int a = Integer.parseInt(str[1]);
			MobilePhone temp;

			try{temp=root.find_phone(a);}		//finds the mobile phone to be switched off

			catch(ObjectException e){
				return actionMessage+": " + e.getMessage();
			}

			try{	
			this.switchOff(temp);
			}		//uses tree method to switch off
			catch(ObjectException e){
				return actionMessage+": " + e.getMessage();
			}

			return "";
			}


		

		else if(str[0].equals("queryNthChild")){
			int a = Integer.parseInt(str[1]);
			int b = Integer.parseInt(str[2])+1;
			Exchange temp=null;
			try{
					temp = findInt(a);		//finds exchange with specified key
			}
			catch(ObjectException e){
				return actionMessage+": " + e.getMessage();
			}

			Exchange temp2;

			try{temp2 = temp.child(b);}		//finds bth child
			catch(ObjectException e)
			{return actionMessage+": " + e.getMessage();}	//if b greater than numChildren
			
			int ans = temp2.getKey();


			return actionMessage+": " + ans;			



		}

		else if(str[0].equals("queryMobilePhoneSet"))
	{
			int a = Integer.parseInt(str[1]);
		Exchange temp=null;
		try{
				temp = findInt(a);			//finds exchange with specified key
		}
		catch(ObjectException e){
			return actionMessage+": " + e.getMessage();
		}
	
		MobilePhoneSet temp2 = temp.residentSet();		//outputs the set of exchange
		String ans = temp2.printSet();		//uses fn of mobilephoneset

		return actionMessage+": " + ans;

	}

		else if(str[0].equals("queryFindPhone") || str[0].equals("findPhone"))
		{
			int a = Integer.parseInt(str[1]);
			MobilePhone temp;
			Exchange ans= null;

			try{temp=root.find_phone(a);}		//finds the mobile phone to be switched off
			catch(ObjectException e)
			{
				return "queryFindPhone "+a+": " + e.getMessage();
			}

			try
			{	
				ans = findPhone(temp);
			}		
			catch(ObjectException e){
				return "queryFindPhone "+a+": " + e.getMessage();
			}

			return "queryFindPhone "+a+": " + ans.getKey();

		}

		else if(str[0].equals("queryLowestRouter") || str[0].equals("lowestRouter"))
		{
			int a = Integer.parseInt(str[1]);
			int b = Integer.parseInt(str[2]);
			Exchange tempa=null,tempb=null;
			try
			{
				tempa = findInt(a);			//finds exchange with specified key
				tempb = findInt(b);			//finds exchange with specified key
			}
			catch(ObjectException e)
			{
				return "queryLowestRouter "+a+" "+b+": " + e.getMessage();
			}

			Exchange lowest = this.lowestRouter(tempa,tempb);

				return "queryLowestRouter "+a+" "+b+": " + lowest.getKey();

		}

		else if(str[0].equals("queryFindCallPath") || str[0].equals("findCallPath"))
		{
			int a = Integer.parseInt(str[1]);
			int b = Integer.parseInt(str[2]);
			MobilePhone tempa=null,tempb=null;ExchangeList route=null;
			try
			{
				tempa=root.find_phone(a);	//finds the mobile phone to be switched off
				tempb=root.find_phone(b);	//finds the mobile phone to be switched off
			}		
			catch(ObjectException e)
			{
				return "queryFindCallPath "+a+" "+b+": " + e.getMessage();
			}

			 try
			 {
			 	route = routeCall(tempa,tempb);
			 }
			 catch(ObjectException e)
			{
				return "queryFindCallPath "+a+" "+b+": " + e.getMessage();
			}
			
			 String ans = route.printExclist();
			
			 return "queryFindCallPath "+a+" "+b+": " + ans;


		}

		else if(str[0].equals("movePhone"))
		{
			int a = Integer.parseInt(str[1]);
			int b = Integer.parseInt(str[2]);
			MobilePhone tempa=null;Exchange tempb=null;

			try
			{
					tempb = findInt(b);		//finds exchange with specified key
			}
			catch(ObjectException e)
			{
					return actionMessage+": " + e.getMessage();
			}
			try{tempa=root.find_phone(a);}		//finds the mobile phone to be switched off

			catch(ObjectException e){
				return actionMessage+": " + e.getMessage();
			}

			this.movePhone(tempa,tempb);

			return "";
		}



	return "";

	}


}


