package com.memegod.lastprice;

import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(LastPrice.MOD_ID)
public class LastPrice {
    public static final String MOD_ID = "lastprice";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LastPrice() {
        LOGGER.info("Last Price horror mod initializing.");
    }
}
