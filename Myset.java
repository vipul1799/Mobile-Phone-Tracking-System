public class Myset<Type>{
	
	SLinkedList<Type> ll;

	public Myset(){
		ll = new SLinkedList<Type>();		//constructor
	}

	public Boolean IsEmpty()
	{
		return ll.isEmpty();		//checks linklist
	}

	public Boolean IsMember(Type o)
	{
		
		Node<Type> temp=ll.getHead();
		Boolean ans = false;

		for(;temp!=null;)
		{
			if(o.equals(temp.getElement()))			
				{
					ans=true;		//if match found
					break;
				}
				temp=temp.getNext();	//traversal

		}

		return ans;
	}

	public void Insert(Type o){
		ll.addLast(o);			//inserts element at last
	}

	public void Delete(Type o) throws ObjectException{
		if(this.IsMember(o)==false){throw new ObjectException("Type not found"); }	//checks for the element

		else
			ll.removeElement(o);		//linkedlist method


	}

	public Myset<Type> Union(Myset<Type> a){
		//*	If element is present in this but not in a then adds it to a and then outputs a *//

		Node<Type> temp = ll.getHead();
		while(temp!=null)
		{
			if(a.IsMember(temp.getElement())==false)	//checks if presen in (a)
				a.Insert(temp.getElement());
			temp=temp.getNext();
		}

		return a;

	} // Union

	public Myset<Type> Intersection(Myset<Type> a){
		/*Creates new set by adding elements in both a and this*/
		Myset<Type> b = new Myset<Type>();		
		Node<Type> temp = ll.getHead();
		while(temp!=null)
		{
			if(a.IsMember(temp.getElement())==true)
				b.Insert(temp.getElement());
			temp=temp.getNext();
		}

		return b;

	} //Intersection
}  //Myset Complete

