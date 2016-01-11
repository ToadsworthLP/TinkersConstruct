package slimeknights.tconstruct.tools.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import slimeknights.tconstruct.tools.tileentity.TilePatternChest;
import slimeknights.tconstruct.tools.tileentity.TileTinkerChest;

public class ContainerPatternChest extends ContainerTinkerStation<TilePatternChest> {

  protected ContainerSideInventory<TilePatternChest> inventory;

  public ContainerPatternChest(InventoryPlayer playerInventory, TilePatternChest tile) {
    super(tile);

    // chest inventory. we have it as a module
    inventory = new DynamicChestInventory(tile, tile, 8, 18, 8); // columns don't matter since they get set by gui
    this.addSubContainer(inventory, true);

    // player inventory
    this.addPlayerInventory(playerInventory, 8, 84);
  }

  public static class DynamicChestInventory extends ContainerSideInventory<TilePatternChest> {

    public DynamicChestInventory(TilePatternChest tile, IInventory inventory, int x, int y, int columns) {
      super(tile, inventory, x, y, columns);

      // add the theoretically possible slots
      while(this.inventorySlots.size() < TileTinkerChest.MAX_INVENTORY) {
        this.addSlotToContainer(createSlot(inventory, this.inventorySlots.size(), 0,0));
      }
    }

    @Override
    protected Slot createSlot(IInventory inventory, int index, int x, int y) {
      return new SlotPatternChest(tile, index, x, y);
    }
  }

  public static class SlotPatternChest extends SlotStencil {

    public final TilePatternChest patternChest;

    public SlotPatternChest(TilePatternChest inventoryIn, int index, int xPosition, int yPosition) {
      super(inventoryIn, index, xPosition, yPosition);

      this.patternChest = inventoryIn;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
      return super.isItemValid(stack) && patternChest.isItemValidForSlot(getSlotIndex(), stack); // slot parameter is unused
    }
  }
}
