\\display the offence speed>65
\\speed.txt
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;



public class speed

    public static class SpeedMap extends Mapper<LongWritable,Text, Text, IntWritable>
       {
         public void map(LongWritable key, Text value, Context con) throws IOException, InterruptedException
          {            
             //int c1=0,c2=0,c3=0,c4=0;
             //float sp1,sp2;
             String [] speed = value.toString().split(",");
            
           int spp=Integer.parseInt(speed[1]);
                    
                     con.write(new Text(speed[0]),new IntWritable (spp));
                
           
            
          }
           
       }    
  
      public static class SpeedReduce extends Reducer<Text,IntWritable,Text, FloatWritable>
       {
          
          public void reduce(Text k, Iterable<IntWritable> v,Context con) throws IOException, InterruptedException
         {
                  
               float a=0,b=0;
              
               for(IntWritable i:v)
               {
                   a++;
               if(i.get()>65)
               {
               b++;
              
              
              
              }
              
              
               }
               con.write(new Text(k),new FloatWritable((b*100)/a));
         
          }
       }           
                  
        
             
  
          
          
            public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException
            {
            Configuration conf = new Configuration();
            conf.set("mapreduce.output.textoutputformat.separator", ",");
            Job job = Job.getInstance(conf, "Total Marks");
            job.setJarByClass(speed.class);
            job.setMapperClass(SpeedMap.class);
            //job.setCombinerClass(CityReduceClass.class);
           job.setReducerClass(SpeedReduce.class);
           job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
           job.setNumReduceTasks(1);
           job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(FloatWritable.class);
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
          }
       }




   

   

	
	
	

