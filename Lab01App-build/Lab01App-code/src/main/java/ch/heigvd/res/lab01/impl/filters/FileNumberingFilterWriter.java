package ch.heigvd.res.lab01.impl.filters;

import ch.heigvd.res.lab01.impl.Utils;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer. When
 * filter encounters a line separator, it sends it to the decorated writer. It then
 * sends the line number and a tab character, before resuming the write process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int nbLine = 1;
    private int previous;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {

        String buf = "";
        String temp = str.substring(off, off + len);

        String[] split = Utils.getNextLine(temp);

        if (nbLine == 1) {
            out.write((nbLine++) + "\t");
        }

        while (!split[0].isEmpty()) {
            buf += split[0] + (nbLine++) + "\t";
            split = Utils.getNextLine(split[1]);
        }
        buf += split[1];
        out.write(buf);

    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        write(cbuf.toString(), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        String buf = "";
        if (nbLine == 1) {
            buf += Integer.toString(nbLine++) + "\t"+((char)c);
        } else {
            if (previous == '\n'||(previous=='\r'&&c!='\n')) {

                buf += Integer.toString(nbLine++) + "\t";
                buf+= ((char)c);
            } else {
                buf += ((char)c);
                
            }
            
        }
        previous=c;
        out.write(buf);
        out.flush();
    }

}
