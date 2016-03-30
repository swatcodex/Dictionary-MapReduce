package Dictionary;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class DictionaryMapper extends Mapper<Text, Text, Text, Text> {
	// TODO define class variables for translation, language, and file name
	String translation, language, pos,fileName;
	Text newKey,value;
	public void setup(Context context) {
		// TODO determine the language of the current file by looking at it's
		// name
		fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
		fileName = fileName.replace(".txt", "");
		language = fileName;
	}

	public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
		// TODO instantiate a tokenizer based on a regex for the file

		// iterate through the tokens of the line, parse, transform and write to
		// context
		if (value.toString().endsWith("]")) {
			StringTokenizer itr = new StringTokenizer(value.toString(), ".*[.*]");
			if (itr.countTokens() == 2) {
				translation = itr.nextToken();
				pos = itr.nextToken();
			}
			newKey = new Text(key.toString() + ": " + "[" + pos + "]");
			value = new Text(language + "_" + translation);
			context.write(newKey,value);
		}
	}

}
