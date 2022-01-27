package mod.tjt01.sprinkle.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "sprinkle");

    public static RegistryObject<SoundEvent> makeSound(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation("sprinkle", name)));
    }

    public static final RegistryObject<SoundEvent> DETECTOR_CLICK_ON = makeSound("block.detector.click_on");
    public static final RegistryObject<SoundEvent> DETECTOR_CLICK_OFF = makeSound("block.detector.click_off");
}
