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
 * The persistent class for the crpnpccorporationresearchfields database table.
 * 
 */
@Entity
@Table(
    name = "crpnpccorporationresearchfields")
public class CrpNpcCorporationResearchField {
  private static final Logger              log = Logger.getLogger(CrpNpcCorporationResearchField.class.getName());

  @EmbeddedId
  private CrpNpcCorporationResearchFieldPK id;

  public CrpNpcCorporationResearchField() {}

  public CrpNpcCorporationResearchField(int skillID, int corporationID) {
    super();
    this.id = new CrpNpcCorporationResearchFieldPK(skillID, corporationID);
  }

  public CrpNpcCorporationResearchFieldPK id() {
    return this.id;
  }

  public int getSkillID() {
    return id.getSkillID();
  }

  public int getCorporationID() {
    return id.getCorporationID();
  }

  public static List<CrpNpcCorporationResearchField> access(
                                                            final int contid,
                                                            final int maxresults,
                                                            final AttributeSelector skillID,
                                                            final AttributeSelector corporationID) {
    try {
      return SDE.getFactory().runTransaction(() -> {
        int maxcount = Math.max(Math.min(maxresults, SDE.DEFAULT_MAX_RESULTS), 1);
        int offset = Math.max(0, contid);
        StringBuilder qs = new StringBuilder();
        // Constrain attributes
        qs.append("SELECT c FROM CrpNpcCorporationResearchField c WHERE 1 = 1");
        AttributeSelector.addIntSelector(qs, "c", "id.skillID", skillID);
        AttributeSelector.addIntSelector(qs, "c", "id.corporationID", corporationID);
        // Return result
        TypedQuery<CrpNpcCorporationResearchField> query = SDE.getFactory().getEntityManager().createQuery(qs.toString(),
                                                                                                           CrpNpcCorporationResearchField.class);
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
    return "CrpNpcCorporationResearchField [id=" + id + "]";
  }

}