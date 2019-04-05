public class Exchange{
	private int key;
	private MobilePhoneSet mset;
	private  ExchangeList childList;
	private Exchange pparent;



	public Exchange(int number){key=number;
		childList = new ExchangeList();
		mset = new MobilePhoneSet();		//constructor
	}
	public Exchange(int number,Exchange par){key=number;pparent=par;childList = new ExchangeList();
		mset = new MobilePhoneSet();} //constructor

	public Boolean isRoot(){		//checks if exchange is root
		if(key==0)				//key=0 specifies root
			return true;
		else
			return false;
	}
	public Exchange parent() throws ObjectException{
		if(this.isRoot()==true)			//exception
			throw new ObjectException("Root has no parent");
		return this.pparent;}

	public int numChildren(){return childList.getSize();}		//gives size of exchangelist
	
	public Exchange child(int i)throws ObjectException{		//gives the ith child with 1 indexing
				Exchange ans=null;
			ans=this.childList.retElement(i).getElement();
					//if i>size of list

			return ans;
	}				

	public ExchangeList chList(){
		
		return this.childList;}
	

	public Boolean isExternal()
	{
		if(childList.getSize()!=0)		//if leaf then true
			return false;
		else
			return true;
	}


	public RoutingMapTree subtree(int i){
		
		Exchange temp=null;

		try{temp=this.child(i);}
		catch(ObjectException e){System.out.println(e);}

		RoutingMapTree a = new RoutingMapTree(temp);		//gives the subtree
		return a;
	}
	public MobilePhoneSet residentSet(){return mset;}

	public int getKey(){return key;}

	public MobilePhone find_phone(int a)throws ObjectException
	{
		MobilePhone ans=null;
		ans=mset.findMob(a);		//uses function of mobilephoneset
		
		return ans;
	}
}

