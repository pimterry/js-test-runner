package org.housered.jstestrunner.io;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

/**
 * Various utilities to encapsulate more any slightly complex file IO operations, 
 * and make them injectable to help testability 
 * 
 * @author tim
 *
 */
@Component
public class IOManager {

	public Collection<File> listFiles(String root) {
	    File rootFile = new File(root);
		return FileUtils.listFiles(rootFile, null, true);
	}

    public boolean isFileADirectory(File testFile) {
        return testFile.isDirectory();
    }
	
}
