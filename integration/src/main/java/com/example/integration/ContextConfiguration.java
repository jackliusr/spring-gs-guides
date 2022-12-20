package com.example.integration;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.MessageHandler;

import com.rometools.rome.feed.synd.SyndEntry;
@Configuration
@EnableIntegration
public class ContextConfiguration {

    @Value("https://spring.io/blog.atom")
    private Resource feedResource;
    
    static final String OUTPUT_DIR = "/tmp/si/blog";
    @Bean
    public AbstractPayloadTransformer<SyndEntry, String> extractLinkFromFeed() {
        return new AbstractPayloadTransformer<SyndEntry, String>() {
            @Override
            protected String transformPayload(SyndEntry payload) {
            	return String.format("%s@%s\n", payload.getTitle(), payload.getLink());
            }
        };

    }
    
    @Bean
    @ServiceActivator(inputChannel= "fileChannel")
    public MessageHandler fileWritingMessageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(OUTPUT_DIR));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        return handler;
    }
    
    @Bean
    public IntegrationFlow feedFlow() {
        return IntegrationFlow
                .from(Feed.inboundAdapter(this.feedResource, "news")
                                .preserveWireFeed(true),
                        e -> e.poller(p -> p.fixedRate(5000)))
                .transform(extractLinkFromFeed())
                .handle(Files.outboundAdapter(new File(OUTPUT_DIR))
                        .fileExistsMode(FileExistsMode.APPEND).appendNewLine(true))
                .get();
    }
   
    
}
