package message;

import java.util.LinkedList;

public class MessageQueue<E>
{
	private LinkedList<E> queue = new LinkedList<>();

	public synchronized void put(E inObj)
	{
		queue.add(inObj);
		notifyAll();
	}

	public synchronized E get()
	{
		try
		{
			while(queue.isEmpty())
			{
				wait();
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public int size()
	{
		return queue.size();
	}
}
