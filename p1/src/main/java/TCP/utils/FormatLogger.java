package TCP.utils;

import java.text.*;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FormatLogger extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getThreadID()+"::"
                +record.getSourceClassName()+"::"
                +record.getSourceMethodName()+"::"
                + new SimpleDateFormat("HHmmssSSS").format(new Date(record.getMillis()))+"> "
                +record.getMessage();
    }
}