package enterprises.orbital.evekit.sde.crp;

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
 * The persistent class for the crpnpccorporationtrades database table.
 * 
 */
@Entity
@Table(
    name = "crpnpccorporationtrades")
public class CrpNpcCorporationTrade {
  private static final Logger      log = Logger.getLogger(CrpNpcCorporationTrade.class.getName());

  @EmbeddedId
  private CrpNpcCorporationTradePK id;

  public CrpNpcCorporationTrade() {}

  public CrpNpcCorporationTrade(int corporationID, int typeID) {
    super();
    this.id = new CrpNpcCorporationTradePK(corporationID, typeID);
  }

  public CrpNpcCorporationTradePK id() {
    return this.id;
  }

  public int getCorporationID() {
    return id.getCorporationID();
  }

  public int getTypeID() {
    return id.getTypeID();
  }

  public static List<CrpNpcCorporationTrade> access(
                                                    final int contid,
                                                    final int maxresults,
                                                    final AttributeSelector corporationID,
                                                    final AttributeSelector typeID) {
    try {
      return SDE.getFactory().runTransaction(() -> {
        int maxcount = Math.max(Math.min(maxresults, SDE.DEFAULT_MAX_RESULTS), 1);
        int offset = Math.max(0, contid);
        StringBuilder qs = new StringBuilder();
        // Constrain attributes
        qs.append("SELECT c FROM CrpNpcCorporationTrade c WHERE 1 = 1");
        AttributeSelector.addIntSelector(qs, "c", "id.corporationID", corporationID);
        AttributeSelector.addIntSelector(qs, "c", "id.typeID", typeID);
        // Return result
        TypedQuery<CrpNpcCorporationTrade> query = SDE.getFactory().getEntityManager().createQuery(qs.toString(), CrpNpcCorporationTrade.class);
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
    return "CrpNpcCorporationTrade [id=" + id + "]";
  }

}