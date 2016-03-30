package Dictionary;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DictionaryReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		String translation = "", lang = "";
		String[] words;
		String trans = "";
		// TODO iterate through values, parse, transform, and write to context

		for (Text value : values) {
			words = value.toString().split("_");
			lang = words[0];
			translation = words[1];
			trans = trans + lang + ":"+translation+"|" ;

		}
		context.write(new Text(key), new Text(trans));
	}
}
