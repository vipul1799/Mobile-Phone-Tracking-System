public class ExchangeList extends SLinkedList<Exchange>{

public String printExclist()
	{
		Node<Exchange> temp = head;

				String ans = "";
		while(temp.getNext()!=null)
		{
			ans =ans+temp.getElement().getKey()+", ";
			temp=temp.getNext();
		}		
		ans=ans+temp.getElement().getKey();
		return ans;
	}


}