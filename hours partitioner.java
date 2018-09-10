//Display the driver id and the hours using partitioner
// input file:pos1 ,pos2
import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.MRCaching.ReduceClass;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.conf.*;

import org.apache.hadoop.fs.*;

import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

import org.apache.hadoop.util.*;

public class hours extends Configured implements Tool
{
   //Map class
	
   public static class MapClass extends Mapper<LongWritable,Text,Text,Text>
   {
      public void map(LongWritable key, Text value, Context context)
      {
         try{
            String[] str = value.toString().split(",");
        //    String itemid=str[0];
            String driverid =str[0];
            String hours =str[2];
            String myrow = driverid + "," + hours;
            context.write(new Text(driverid), new Text(myrow));
         }
         catch(Exception e)
         {
            System.out.println(e.getMessage());
         }
      }
   }
   
   //Reducer class
	
   public static class ReduceClass extends Reducer<Text,Text,Text,Text>
   {
   

    

      public void reduce(Text key, Iterable <Text> values, Context context) throws IOException, InterruptedException
      {
         
          for (Text val : values) {
          String str[] = val.toString().split(",");
         context.write(key,new Text(str[1])); 
         }
        
        
		
	
        
      }
   }
   
   //Partitioner class
	
   public static class CaderPartitioner extends
   Partitioner < Text, Text >
   {
      @Override
      public int getPartition(Text key, Text value, int numReduceTasks)
      {
         String[] str = value.toString().split(",");
         int hours=Integer.parseInt(str[1]);
         if(hours>=0 && hours<=50)
         {
            return 0;
         }
         else if(hours>=51 && hours<=70 )
         {
            return 1 ;
         }
         else if(hours>=71 && hours<=90)
         {
            return 2 ;
         }
            else
            {
            	return 3;
            }
         }
         
      }
   
   
   

   public int run(String[] arg) throws Exception
   {
	
	   
	  Configuration conf = new Configuration();
	  Job job = Job.getInstance(conf);
	  job.setJarByClass(hours.class);
	  job.setJobName("State Wise Item Qty sales");
      FileInputFormat.setInputPaths(job, new Path(arg[0]));
      FileOutputFormat.setOutputPath(job,new Path(arg[1]));
		
      job.setMapperClass(MapClass.class);
		
      job.setMapOutputKeyClass(Text.class);
      job.setMapOutputValueClass(Text.class);
      
      //set partitioner statement
		
      job.setPartitionerClass(CaderPartitioner.class);
      job.setReducerClass(ReduceClass.class);
      job.setNumReduceTasks(4);
      job.setInputFormatClass(TextInputFormat.class);
		
      job.setOutputFormatClass(TextOutputFormat.class);
      
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(Text.class);
		
      System.exit(job.waitForCompletion(true)? 0 : 1);
      return 0;
   }
   
   public static void main(String ar[]) throws Exception
   {
      ToolRunner.run(new Configuration(), new hours(),ar);
      System.exit(0);
   }
}









