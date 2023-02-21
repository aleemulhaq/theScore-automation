import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.appender.ConsoleAppender
import org.apache.logging.log4j.core.config.Configurator
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory
import java.time.LocalDateTime

// set up logging here as this is the entry point of the application
fun setUpLogging() {
    val builder = ConfigurationBuilderFactory.newConfigurationBuilder()
    // naming the logger configuration
    builder.setConfigurationName("DefaultLogger")
    // create a console appender
    val appenderBuilder = builder.newAppender("Console", "CONSOLE")
        .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)
    // add a layout like pattern, json etc
    appenderBuilder.add(
        builder.newLayout("PatternLayout")
            .addAttribute("pattern", "%d %p %c [%t] %m%n")
    )

    // create a file appender
    val file = builder.newAppender("log", "File")
    file.addAttribute("fileName", "logs/${LocalDateTime.now()}.log")
    file.add(
        builder.newLayout("PatternLayout")
            .addAttribute("pattern", "%d %p %c [%t] %m%n")
    )

    // create a root appender
    val rootLogger = builder.newRootLogger(Level.INFO)
    rootLogger.add(builder.newAppenderRef("log"))
    rootLogger.add(builder.newAppenderRef("Console"))

    builder.setStatusLevel(Level.INFO)
    builder.add(rootLogger)
    builder.add(appenderBuilder)
    builder.add(file)
    Configurator.reconfigure(builder.build())
}