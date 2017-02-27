
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import static ch.qos.logback.classic.Level.INFO

scan( "30 seconds" )
def LOG_PATH = "logs"

appender( "Console-Appender", ConsoleAppender ) {
    encoder( PatternLayoutEncoder ) {
        pattern = "%-30(%d{HH:mm:ss.SSS} [%thread]) %highlight(%-5level) %logger{32} - %msg%n"
    }
}

appender( "File-Appender", FileAppender ) {
    file = "${LOG_PATH}/logfile.txt"
    append = false // started fresh when the application restarts
    encoder( PatternLayoutEncoder ) {
        pattern = "%-30(%d{HH:mm:ss.SSS} [%thread]) %-5level %logger{32} - %msg%n"
        outputPatternAsHeader = true
    }
}

logger( "com.vherasymenko.avro",INFO,["File-Appender"] )
root( INFO,["Console-Appender"] )