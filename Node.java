public class Node<Type>{
	private Type element;
	private Node<Type> next;

	public Node(){
		element=null;next=null;			//constructor
	}

	public Node(Type s,Node<Type> n)	//constructor
	{
		element = s;
		next = n; 
	}

	public Type getElement(){return element;}	
	public Node<Type> getNext(){return next;}

	public void setElement(Type newElem){element = newElem;}
	public void setNext(Node<Type> newNext){next = newNext;}

}