//display density of the word in the record
//inputfile:movies.txt

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;




public class recordcount {

	public static class TokenizerMapper
       extends Mapper< LongWritable,Text,Text,Text>
	{

    //private final static IntWritable one = new IntWritable(1);
    private Text sentence = new Text();
    
    public void map(LongWritable key, Text value, Context context
                    ) throws IOException, InterruptedException {
    	String mySearchText = context.getConfiguration().get("myText");
    	String line = value.toString();
        String newline = line.toLowerCase();
    	String newText = mySearchText.toLowerCase();
    	String n="";
    	if(mySearchText != null)
      {
    	  if(newline.contains(newText))
          {
    		   n="1"+","+newline;

    		  sentence.set(n);
    		  context.write( new Text("one") , sentence);
         }
    	  else
    		  
              {
    		  n="2"+","+newline;
        		  sentence.set(n);
        		  context.write( new Text("one") ,sentence);
             }  
      
      }
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,Text,Text,FloatWritable> {

    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int count = 0,count1=0;
      for (Text val : values) {
        String a[]=val.toString().split(",");
        count++;
        //int i=Integer.parseInt(a[0]);
        if(a[0].contains("1"))
        {
        	
			count1++;
        	
        }
      }
      
	float per=((float)count1/(float)count)*100;
      context.write(key,new FloatWritable(per));
    }
  }

  public static void main(String[] args) throws Exception {
	
	Configuration conf = new Configuration();
    conf.set("mapreduce.output.textoutputformat.separator","|");
	
	//System.out.println(args.length);
    
    
    
    
  //System.out.println(args[2]);
	  if(args.length > 2)
      {
		  conf.set("myText", args[2]);
      }
	  else
	  {
		  System.out.println("Number of arguments should be 3");
		  System.exit(0);
	  }
	Job job = Job.getInstance(conf, "String Search");

	job.setJarByClass(recordcount.class);
    job.setMapperClass(TokenizerMapper.class);
   // job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setNumReduceTasks(1);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(FloatWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
