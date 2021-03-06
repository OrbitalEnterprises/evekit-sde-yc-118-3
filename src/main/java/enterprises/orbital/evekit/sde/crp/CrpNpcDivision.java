package enterprises.orbital.evekit.sde.crp;

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
 * The persistent class for the crpnpcdivisions database table.
 * 
 */
@Entity
@Table(
    name = "crpnpcdivisions")
public class CrpNpcDivision {
  private static final Logger log = Logger.getLogger(CrpNpcDivision.class.getName());

  @Id
  private int                divisionID;
  private String              divisionName;
  private String              description;
  private String              leaderType;

  public CrpNpcDivision() {}

  public CrpNpcDivision(int divisionID, String description, String divisionName, String leaderType) {
    super();
    this.divisionID = divisionID;
    this.description = description;
    this.divisionName = divisionName;
    this.leaderType = leaderType;
  }

  public int getDivisionID() {
    return this.divisionID;
  }

  public String getDescription() {
    return this.description;
  }

  public String getDivisionName() {
    return this.divisionName;
  }

  public String getLeaderType() {
    return this.leaderType;
  }

  public static List<CrpNpcDivision> access(
                                            final int contid,
                                            final int maxresults,
                                            final AttributeSelector divisionID,
                                            final AttributeSelector description,
                                            final AttributeSelector divisionName,
                                            final AttributeSelector leaderType) {
    try {
      return SDE.getFactory().runTransaction(() -> {
        int maxcount = Math.max(Math.min(maxresults, SDE.DEFAULT_MAX_RESULTS), 1);
        int offset = Math.max(0, contid);
        StringBuilder qs = new StringBuilder();
        // Constrain attributes
        qs.append("SELECT c FROM CrpNpcDivision c WHERE 1 = 1");
        AttributeParameters p = new AttributeParameters("att");
        AttributeSelector.addIntSelector(qs, "c", "divisionID", divisionID);
        AttributeSelector.addStringSelector(qs, "c", "description", description, p);
        AttributeSelector.addStringSelector(qs, "c", "divisionName", divisionName, p);
        AttributeSelector.addStringSelector(qs, "c", "leaderType", leaderType, p);
        // Return result
        TypedQuery<CrpNpcDivision> query = SDE.getFactory().getEntityManager().createQuery(qs.toString(), CrpNpcDivision.class);
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
    return "CrpNpcDivision [divisionID=" + divisionID + ", description=" + description + ", divisionName=" + divisionName + ", leaderType=" + leaderType + "]";
  }

}