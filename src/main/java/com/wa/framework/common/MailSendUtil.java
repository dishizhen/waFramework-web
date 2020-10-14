package com.wa.framework.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述：发邮件工具类
 * 创建人：guoyt
 * 创建时间：2016年2月26日下午1:40:33
 * 修改人：guoyt
 * 修改时间：2016年2月26日下午1:40:33
 */
public class MailSendUtil {

    private static final Log logger = LogFactory.getLog(MailSendUtil.class);

    public static void sendMail(Email email) {
        Properties property = new Properties();
        property.setProperty("mail.transport.protocol", "smtp");
        property.setProperty("mail.smtp.auth", "true");
        property.put("mail.smtp.starttls.enable", "true");
        property.put("mail.smtp.port", email.getPort());
        property.put("mail.smtp.socketFactory.port", email.getPort());
        property.put("mail.smtp.socketFactory.fallback", "false");
        property.put("mail.smtp.host", email.getHost());
        Session session = Session.getDefaultInstance(property, null);

        // 启动JavaMail调试功能，可以返回与SMTP服务器交互的命令信息
        // 可以从控制台中看一下服务器的响应信息
        session.setDebug(false);

        String from = email.getFrom();
        String subject = email.getSubject();
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, email.getFromName()));
            Address[] tos = null;
            if ((email.getTo() != null) && (!email.getTo().isEmpty())) {
                List<String> toList = email.getTo();
                tos = new InternetAddress[toList.size()];
                for (int i = 0; i < toList.size(); i++) {
                    String address = toList.get(i);
                    tos[i] = new InternetAddress(address);
                }
            } else {
                return;
            }
            msg.setRecipients(Message.RecipientType.TO, tos);
            Address[] ccs = null;
            if ((email.getCc() != null) && (!email.getCc().isEmpty())) {
                List<String> ccList = email.getCc();
                ccs = new InternetAddress[ccList.size()];
                for (int i = 0; i < ccList.size(); i++) {
                    String ccAddress = ccList.get(i);
                    ccs[i] = new InternetAddress(ccAddress);
                }
                msg.setRecipients(Message.RecipientType.CC, ccs);
            }
            msg.setSubject(subject);
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(email.getContent(), "text/html;charset=utf-8");
            mp.addBodyPart(mbp);
            List<String> files = email.getFile();
            if ((files != null) && (!files.isEmpty())) {// 有附件
                String filename = "";
                for (int i = 0; i < files.size(); i++) {
                    mbp = new MimeBodyPart();
                    filename = files.get(i); // 选择出每一个附件名
                    FileDataSource fds = new FileDataSource(filename); // 得到数据源
                    mbp.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
                    mbp.setFileName(MimeUtility.encodeText(fds.getName().substring(14)));  // 得到文件名同样至入BodyPart
                    mp.addBodyPart(mbp);
                }
            }
            msg.setContent(mp);
            msg.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(email.getHost(), email.getUsername(), email.getPassword());
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public static void main(String[] args) {
        Email email = new Email();
        email.setHost("smtp.citgc.com");
        email.setPort(25);
        email.setFrom("disz@citgc.com");
        List<String> listTo = new ArrayList<String>();
        listTo.add("1024599464@qq.com");
        email.setTo(listTo);
        List<String> listCc = new ArrayList<String>();
        listCc.add("1024599464@qq.com");
        email.setCc(listCc);
        email.setFromName("未至科技开发平台");
        email.setSubject("aaa");
        email.setContent("内容ccc22222222");
        email.setUsername("disz@citgc.com");
        email.setPassword("1995DszBis");
//        List<String> listFile = new ArrayList<String>();
//        listFile.add("E:\\doc\\DB\\13bnet.sql");
//        listFile.add("E:\\doc\\FLEX4_Flex4教程.doc");
//        email.setFile(listFile);
        try {
            sendMail(email);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
