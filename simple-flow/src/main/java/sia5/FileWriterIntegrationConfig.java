package sia5;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
public class FileWriterIntegrationConfig {

	@Bean
	@Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
	public GenericTransformer<String, String> upperCaseTransformer() {
		return text -> text.toUpperCase();
	}

	@Bean
	@ServiceActivator(inputChannel = "fileWriterChannel")
	public FileWritingMessageHandler fileWriter() {

		FileWritingMessageHandler writer = new FileWritingMessageHandler(new File("/tmp/sia5/files"));

		writer.setExpectReply(false);
		writer.setFileExistsMode(FileExistsMode.APPEND);
		writer.setAppendNewLine(true);

		return writer;
	}

	
	@Bean
	public MessageChannel textInChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageChannel fileWritterChannel() {
		return new DirectChannel();
	}
}
