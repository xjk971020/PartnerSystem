package cn.dezhi.welfare.partner;

import cn.dezhi.welfare.partner.service.impl.SolrService;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.apache.solr.client.solrj.SolrClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author xjk
 * @date 2019/4/26 -  21:08
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrServiceTest {

    private static Logger logger = LoggerFactory.getLogger(SolrServiceTest.class);

    @Autowired
    SolrService solrService;

    @Autowired
    SolrClient solrClient;

    @Test
    public void test() {
        System.out.println(solrService.searchGoods("str", 1, 1));
    }

    @Test
    public void test2() throws MalformedURLException {
            //    输出Ascii格式
            Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                    .withGeneratedExamples()
                    .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                    .build();

            Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                    .withConfig(config)
                    .build()
                    .toFile(Paths.get("src/docs/asciidoc/generated/api"));
    }

    @Test
    public void generateAsciiDocs() throws Exception {
        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/asciidoc/generated"));
    }
}
