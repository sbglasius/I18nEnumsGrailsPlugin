package grails.plugin.i18n.enums

import grails.util.Holders
import org.springframework.context.MessageSource

/**
 * Created by Admin on 01.05.2014.
 */
class MessageSourceHolder {

    private static MessageSource messageSource

    public static MessageSource getMessageSource() {
        synchronized(MessageSourceHolder.class) {
            if(messageSource!=null) return messageSource
        }
        Holders.applicationContext.getBean('messageSource') as MessageSource
    }

    public synchronized static void setMessageSource(MessageSource ms) {
        this.messageSource = ms
    }

}
