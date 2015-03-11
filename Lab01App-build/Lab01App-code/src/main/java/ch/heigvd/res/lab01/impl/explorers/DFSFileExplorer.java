package ch.heigvd.res.lab01.impl.explorers;

import ch.heigvd.res.lab01.interfaces.IFileExplorer;
import ch.heigvd.res.lab01.interfaces.IFileVisitor;
import java.io.File;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered node
 * (file and directory). When the explorer reaches a directory, it visits all files
 * in the directory and then moves into the subdirectories.
 *
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

    @Override
    public void explore(File rootDirectory, IFileVisitor vistor) {
        vistor.visit(rootDirectory);
        if (!rootDirectory.exists()) {
            return;
        }
        if (rootDirectory.isDirectory()) {
            File[] files = rootDirectory.listFiles();

            for (File f : files) {
                if (f.isFile()) {
                    explore(f, vistor);
                }
            }
            for (File f : files) {
                if (f.isDirectory()) {
                    explore(f, vistor);
                }
            }
        }

    }

}
