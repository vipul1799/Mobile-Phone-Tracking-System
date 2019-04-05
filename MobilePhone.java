public class MobilePhone{
	private int id;
	private Boolean flag;
	private Exchange base;

	public MobilePhone(int number)
	{
		id=number;
		flag=false; //true means on false is off
	}

	public MobilePhone(int number,Exchange loc)
	{
		id=number;
		base=loc;
		flag=false; //true means on false is off
	}

	public int number(){
		return id;			
	}

	public Boolean status(){
		return flag;
	}

	public void switchOn(){
		flag=true;
	}

	public void switchOff(){
		flag=false;
	}

	public void changeLoc(Exchange loc)
	{
		base=loc;
	}

	public Exchange location(){
		return base;
	}

}    //MobilePhone comp
