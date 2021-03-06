package enterprises.orbital.evekit.sde.dgmtests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import enterprises.orbital.evekit.sde.AttributeSelector;
import enterprises.orbital.evekit.sde.TestSetup;
import enterprises.orbital.evekit.sde.dgm.DgmEffect;

public class TestDgmEffect extends TestSetup {
  @Test
  public void testTableCount() {
    int maxresults = 1000;
    int contid = 0;
    AttributeSelector all = new AttributeSelector("{any:true}");
    List<DgmEffect> next = DgmEffect.access(contid, maxresults, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all,
                                            all, all, all, all, all, all, all, all, all);
    while (!next.isEmpty()) {
      contid += next.size();
      next = DgmEffect.access(contid, maxresults, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all, all,
                              all, all, all, all, all, all);
    }
    Assert.assertEquals(4157, contid);
  }

  @Test
  public void testRandomElement() {
    AttributeSelector all = new AttributeSelector("{any:true}");
    List<DgmEffect> next = DgmEffect.access(0, 1000, new AttributeSelector("{values:[13]}"), all, all, all, all, all, all, all, all, all, all, all, all, all,
                                            all, all, all, all, all, all, all, all, all, all, all, all, all, all);
    Assert.assertEquals(1, next.size());
    DgmEffect random = next.get(0);
    Assert.assertNotNull(random);
    Assert.assertEquals(13, random.getEffectID());
    Assert.assertEquals("medPower", random.getEffectName());
    Assert.assertEquals(0, random.getEffectCategory());
    Assert.assertEquals(131, random.getPreExpression());
    Assert.assertEquals(131, random.getPostExpression());
    Assert.assertEquals("Requires a medium power slot.", random.getDescription());
    Assert.assertEquals("", random.getGuid());
    Assert.assertEquals(new Integer(294), random.getIconID());
    Assert.assertFalse(random.isOffensive());
    Assert.assertFalse(random.isAssistance());
    Assert.assertNull(random.getDurationAttributeID());
    Assert.assertNull(random.getTrackingSpeedAttributeID());
    Assert.assertNull(random.getDischargeAttributeID());
    Assert.assertNull(random.getRangeAttributeID());
    Assert.assertNull(random.getFalloffAttributeID());
    Assert.assertFalse(random.isDisallowAutoRepeat());
    Assert.assertTrue(random.isPublished());
    Assert.assertEquals("Medium power", random.getDisplayName());
    Assert.assertFalse(random.isWarpSafe());
    Assert.assertFalse(random.isRangeChance());
    Assert.assertFalse(random.isElectronicChance());
    Assert.assertFalse(random.isPropulsionChance());
    Assert.assertNull(random.isDistribution());
    Assert.assertEquals(null, random.getSfxName());
    Assert.assertNull(random.getNpcUsageChanceAttributeID());
    Assert.assertNull(random.getNpcActivationChanceAttributeID());
    Assert.assertNull(random.getFittingUsageChanceAttributeID());
    Assert.assertNull(random.getModifierInfo());
    Assert.assertNotNull(random.toString());
  }
}
