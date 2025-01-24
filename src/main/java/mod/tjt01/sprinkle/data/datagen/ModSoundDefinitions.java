package mod.tjt01.sprinkle.data.datagen;

import mod.tjt01.sprinkle.init.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitions extends SoundDefinitionsProvider {

    protected ModSoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, "sprinkle", helper);
    }

    @Override
    public void registerSounds() {
        this.add(ModSoundEvents.DETECTOR_CLICK_ON.get(), definition().with(
                sound("random/click").pitch(0.9).volume(0.5)
        ).subtitle("subtitle.sprinkle.block.detector.click"));
        this.add(ModSoundEvents.DETECTOR_CLICK_OFF.get(), definition().with(
                sound("random/click").pitch(0.75).volume(0.5)
        ).subtitle("subtitle.sprinkle.block.detector.click"));
    }

    @Override
    public String getName() {
        return "Sound Definitions for Sprinkle";
    }
}
