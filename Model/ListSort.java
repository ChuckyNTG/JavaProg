import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ListSort
{
	//Sorting and displaying jobs
	private ArrayList<Job> list;
	//Accessing jobs
	private HashMap<Integer,Job> _jobs;
	
	public ListSort()
	{
		list = new ArrayList<Job>();
		_jobs = new HashMap<Integer,Job>();
	}
	
	public void add(Job job)
	{
		list.add(job);
		
		int id = job.getID();
		_jobs.put(id,job);

		sort();
	}

	public void remove(int entry, int ID) 
	{
		list.remove(entry-1);
		_jobs.remove(ID);	

		sort();
	}
	
	public void getJob(int id)
	{
		return _jobs.get(id);
	}

	public void sort()
	{
		sort("date");
	}

	public void sort(String attribute)
	{
		list.sort(new Comparator<Job>() {
			@Override
			public int compare(Job job, Job t1) {
				return job.getAtrribute(attribute).compareTo(t1.getAtrribute(attribute));
			}
		});
	}
	/*
	public void inSort(int position, Job value)
	{
		int j = position -1;

		while(j>=0 && list.get(j).getDate().getTime().compareTo(value.getDate().getTime())>0)
		{
			list.set(j+1,list.get(j));
			j=j-1;
		}
		list.set(j+1,value);
	}
	*/
	public void printList()
	{
		System.out.println("The details of the list:");
	
		for(Job job:list)
		{
			System.out.println(job.toString());				
		}
	}

	@Override
	public String toString(){
		String out = "";
		for(Job job : list){
			if(out.length() >= 1)
				out += '\n';
			out += job.toString();
		}
		return out;
	}
}
