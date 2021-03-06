package enterprises.orbital.evekit.sde.sta;

import enterprises.orbital.evekit.sde.AttributeSelector;
import enterprises.orbital.evekit.sde.SDE;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The persistent class for the staoperationservices database table.
 * 
 */
@Entity
@Table(
    name = "staoperationservices")
public class StaOperationService {
  private static final Logger   log = Logger.getLogger(StaOperationService.class.getName());

  @EmbeddedId
  private StaOperationServicePK id;

  public StaOperationService() {}

  public StaOperationService(byte operationID, int serviceID) {
    super();
    this.id = new StaOperationServicePK(operationID, serviceID);
  }

  public StaOperationServicePK id() {
    return this.id;
  }

  public int getOperationID() {
    return id.getOperationID();
  }

  public int getServiceID() {
    return id.getServiceID();
  }

  public static List<StaOperationService> access(
                                                 final int contid,
                                                 final int maxresults,
                                                 final AttributeSelector operationID,
                                                 final AttributeSelector serviceID) {
    try {
      return SDE.getFactory().runTransaction(() -> {
        int maxcount = Math.max(Math.min(maxresults, SDE.DEFAULT_MAX_RESULTS), 1);
        int offset = Math.max(0, contid);
        StringBuilder qs = new StringBuilder();
        // Constrain attributes
        qs.append("SELECT c FROM StaOperationService c WHERE 1 = 1");
        AttributeSelector.addIntSelector(qs, "c", "id.operationID", operationID);
        AttributeSelector.addIntSelector(qs, "c", "id.serviceID", serviceID);
        // Return result
        TypedQuery<StaOperationService> query = SDE.getFactory().getEntityManager().createQuery(qs.toString(), StaOperationService.class);
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
    return "StaOperationService [id=" + id + "]";
  }

}