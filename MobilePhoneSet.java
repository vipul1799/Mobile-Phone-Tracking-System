public class MobilePhoneSet extends Myset<MobilePhone>{
	

/*Chcks if the set has the mobilephone with specified number*/
	public Boolean hasMob(int a){			
		Node<MobilePhone> temp = ll.getHead();
		Boolean ans = false;
		while(temp!=null){
		if(temp.getElement().number()==a)
			{ans=true;
				break;}

				temp=temp.getNext();		//traversal

		}
		return ans;

	}

	/*Finds the mobilephone with specified number if present in set*/
	public MobilePhone findMob(int a) throws ObjectException{
		MobilePhone ans=null;
		Node<MobilePhone> temp = ll.getHead();
		while(temp!=null){
		if(temp.getElement().number()==a)
			{ans=temp.getElement();
				break;}

				temp=temp.getNext();

		}

		if(temp==null)
			throw new ObjectException("Error - No mobile phone with identifier "+a+" found in the network");

		return ans;

	}

	/*Prints the mobile phone set*/
	public String printSet(){
		Node<MobilePhone> temp = ll.getHead();

				String ans = "";
		while(temp.getNext()!=null){

		if(temp.getElement().status())
		{
			ans =ans+temp.getElement().number()+", ";
		}		//appends the numbers to string 
					temp=temp.getNext();

		}
		if(temp.getElement().status())
		{
			ans=ans+temp.getElement().number();
		}
		else
			ans=ans+"\b\b";
		return ans;
	}
}