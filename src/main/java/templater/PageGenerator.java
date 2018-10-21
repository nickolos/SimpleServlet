package templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {

    private static final String TEMPLATES_DIR = "templates";

    private static PageGenerator pageGenerator;
    private final Configuration config;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();

        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = config.getTemplate(TEMPLATES_DIR + File.separator + filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            return e.getMessage();
        }

        return stream.toString();
    }

    private PageGenerator(){
        config = new Configuration();
    }


}
