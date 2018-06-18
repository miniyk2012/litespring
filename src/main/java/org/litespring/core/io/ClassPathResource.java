package org.litespring.core.io;

import org.litespring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by thomas_young on 18/6/2018.
 */
public class ClassPathResource implements Resource {
    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader);
    }

    @Override
    public String getDescription() {
        return this.path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = this.classLoader.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(path + " can not opened!");
        }
        return is;
    }
}
