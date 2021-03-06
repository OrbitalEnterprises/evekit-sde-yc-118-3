package enterprises.orbital.evekit.sde.inv;

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
 * The persistent class for the invmetagroups database table.
 * 
 */
@Entity
@Table(
    name = "invmetagroups")
public class InvMetaGroup {
  private static final Logger log = Logger.getLogger(InvMetaGroup.class.getName());

  @Id
  private int                 metaGroupID;
  private String              metaGroupName;
  private String              description;
  private Integer             iconID;

  public InvMetaGroup() {}

  public InvMetaGroup(int metaGroupID, String description, Integer iconID, String metaGroupName) {
    super();
    this.metaGroupID = metaGroupID;
    this.description = description;
    this.iconID = iconID;
    this.metaGroupName = metaGroupName;
  }

  public int getMetaGroupID() {
    return this.metaGroupID;
  }

  public String getDescription() {
    return this.description;
  }

  public Integer getIconID() {
    return this.iconID;
  }

  public String getMetaGroupName() {
    return this.metaGroupName;
  }

  public static List<InvMetaGroup> access(
                                          final int contid,
                                          final int maxresults,
                                          final AttributeSelector metaGroupID,
                                          final AttributeSelector description,
                                          final AttributeSelector iconID,
                                          final AttributeSelector metaGroupName) {
    try {
      return SDE.getFactory().runTransaction(() -> {
        int maxcount = Math.max(Math.min(maxresults, SDE.DEFAULT_MAX_RESULTS), 1);
        int offset = Math.max(0, contid);
        StringBuilder qs = new StringBuilder();
        // Constrain attributes
        qs.append("SELECT c FROM InvMetaGroup c WHERE 1 = 1");
        AttributeParameters p = new AttributeParameters("att");
        AttributeSelector.addIntSelector(qs, "c", "metaGroupID", metaGroupID);
        AttributeSelector.addStringSelector(qs, "c", "description", description, p);
        AttributeSelector.addIntSelector(qs, "c", "iconID", iconID);
        AttributeSelector.addStringSelector(qs, "c", "metaGroupName", metaGroupName, p);
        // Return result
        TypedQuery<InvMetaGroup> query = SDE.getFactory().getEntityManager().createQuery(qs.toString(), InvMetaGroup.class);
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
    return "InvMetaGroup [metaGroupID=" + metaGroupID + ", description=" + description + ", iconID=" + iconID + ", metaGroupName=" + metaGroupName + "]";
  }

}