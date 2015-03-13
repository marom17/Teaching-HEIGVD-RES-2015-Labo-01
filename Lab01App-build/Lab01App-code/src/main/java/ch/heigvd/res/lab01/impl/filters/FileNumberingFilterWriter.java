package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int nbLine=1;
  private String previous="";
  private boolean first=true;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      
      String sep="";
      String buf="";
      String temp=str.substring(off, len-off);
      if(!previous.equals("")){
          if(temp.contains("\r\n")||previous.contains("\r\n")){
              sep="\r\n";
          }
          else if(temp.contains("\n")||previous.contains("\n")){
              sep="\n";
          }
          else if(temp.contains("\r")||previous.contains("\r")){
              sep="\r";
          }
      }
      else{
          if(temp.contains("\r\n")){
              sep="\r\n";
          }
          else if(temp.contains("\n")){
              sep="\n";
          }
          else if(temp.contains("\r")){
              sep="\r";
          }
      }
      String[] split=temp.split(sep);
      if(previous.equals("")){
          buf+=nbLine+"\t";
          nbLine++;
      }
      /*
      else if(previous.endsWith(sep)){
          buf+=nbLine+"\t";
          nbLine++;
      }*/
      buf+=split[0];
      if(split.length>1){
          buf+=sep;
      }
      for(int i=1;i<split.length;i++){
          buf+=nbLine+"\t"+split[i];
          nbLine++;
          if(i!=split.length-1){
              buf+=sep;
          }
      }
      if(temp.endsWith(sep)){
          buf+=sep+nbLine+"\t";
          nbLine++;
      }
      previous="";
      previous=previous.concat(buf);
      super.write(buf, off, buf.length());
      
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(cbuf.toString(),off,len);
  }

  @Override
  public void write(int c) throws IOException {
          if(first){
              first=false;
              super.write(Integer.toString(nbLine));
              nbLine++;
          }
          if(c==(int)'\n'){
              
              super.write((char)c);
              super.write(Integer.toString(nbLine));
              nbLine++;
            }
          else{
              super.write((char)c);
          }
          super.flush();
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
