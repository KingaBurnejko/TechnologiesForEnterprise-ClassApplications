package lab.ejb;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@JMSDestinationDefinition(name = "java:app/jms/NewsQueue",
        interfaceName = "jakarta.jms.Queue", resourceAdapter = "jmsra",
        destinationName = "NewsQueue")

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName =
                "destinationLookup", propertyValue = "java:app/jms/NewsQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "jakarta.jms.Queue")
})
public class NewsMDB implements MessageListener {
    @PersistenceContext(unitName = "EnterpriseApp-ejbPU")
    private EntityManager em;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                String[] parts = text.split("\\|", 2);

                if (parts.length == 2) {
                    NewsItem newsItem = new NewsItem();
                    newsItem.setHeading(parts[0]);
                    newsItem.setBody(parts[1]);
                    em.persist(newsItem);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
