package org.litespring.core.io;

import org.litespring.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by thomas_young on 18/6/2018.
 */
public class FileSystemResource implements Resource {
    private String path;
    private File file;

    public FileSystemResource(String path) {
        Assert.notNull(path, "path must be not null.");
        this.file = new File(path);
        this.path = path;
    }

    @Override
    public String getDescription() {
        return "file [" + this.file.getAbsolutePath() + "]";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }
}
