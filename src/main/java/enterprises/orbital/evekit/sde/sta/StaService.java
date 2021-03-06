package enterprises.orbital.evekit.sde.sta;

import enterprises.orbital.evekit.sde.AttributeParameters;
import enterprises.orbital.evekit.sde.AttributeSelector;
import enterprises.orbital.evekit.sde.SDE;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The persistent class for the staservices database table.
 * 
 */
@Entity
@Table(
    name = "staservices")
public class StaService {
  private static final Logger log = Logger.getLogger(StaService.class.getName());

  @Id
  private int                 serviceID;
  private String              serviceName;
  private String              description;

  public StaService() {}

  public StaService(int serviceID, String description, String serviceName) {
    super();
    this.serviceID = serviceID;
    this.description = description;
    this.serviceName = serviceName;
  }

  public int getServiceID() {
    return this.serviceID;
  }

  public String getDescription() {
    return this.description;
  }

  public String getServiceName() {
    return this.serviceName;
  }

  public static List<StaService> access(
                                        final int contid,
                                        final int maxresults,
                                        final AttributeSelector serviceID,
                                        final AttributeSelector description,
                                        final AttributeSelector serviceName) {
    try {
      return SDE.getFactory().runTransaction(() -> {
        int maxcount = Math.max(Math.min(maxresults, SDE.DEFAULT_MAX_RESULTS), 1);
        int offset = Math.max(0, contid);
        StringBuilder qs = new StringBuilder();
        // Constrain attributes
        qs.append("SELECT c FROM StaService c WHERE 1 = 1");
        AttributeParameters p = new AttributeParameters("att");
        AttributeSelector.addIntSelector(qs, "c", "serviceID", serviceID);
        AttributeSelector.addStringSelector(qs, "c", "description", description, p);
        AttributeSelector.addStringSelector(qs, "c", "serviceName", serviceName, p);
        // Return result
        TypedQuery<StaService> query = SDE.getFactory().getEntityManager().createQuery(qs.toString(), StaService.class);
        p.fillParams(query);
        query.setMaxResults(maxcount);
        query.setFirstResult(offset);
        return query.getResultList();
      });
    } catch (Exception e) {
      log.log(Level.SEVERE, "query error", e);
    }
    return Collections.emptyList();
  }

  @Override
  public String toString() {
    return "StaService [serviceID=" + serviceID + ", description=" + description + ", serviceName=" + serviceName + "]";
  }

}