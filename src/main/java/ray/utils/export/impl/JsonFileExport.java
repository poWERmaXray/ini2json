package ray.utils.export.impl;

import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import ray.utils.export.FileExport;
import ray.utils.reader.FileReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class JsonFileExport implements FileExport {
    @Autowired
    private FileReader reader;

    @Override
    public File exportJSONFile(File file, File outputFile, Object genericObject) {
        Object iniInstance = reader.getInstance(Path.of(file.getPath()), genericObject);

        String exportString = JSON.toJSONString(iniInstance);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {
            writer.write(exportString);
        } catch (Exception ignore) {
        }
        return outputFile;
    }
}