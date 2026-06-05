package com.robson.efmextended.combosystem;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class ComboStart {


    private final ComboNode lightNode;

    private final ComboNode heavyNode;

    private final AnimationManager.AnimationAccessor<? extends StaticAnimation> lightDash;

    private final AnimationManager.AnimationAccessor<? extends StaticAnimation> lightAirslash;

    private final AnimationManager.AnimationAccessor<? extends StaticAnimation> heavyDash;

    private final AnimationManager.AnimationAccessor<? extends StaticAnimation> heavyAirslash;


    public ComboStart(ComboNode lightNode, ComboNode heavyNode,
        AnimationManager.AnimationAccessor<? extends StaticAnimation> lightDash,
        AnimationManager.AnimationAccessor<? extends StaticAnimation> lightAirslash,
        AnimationManager.AnimationAccessor<? extends StaticAnimation> heavyDash,
        AnimationManager.AnimationAccessor<? extends StaticAnimation> heavyAirslash
    ){
        this.lightNode = lightNode;
        this.heavyNode = heavyNode;
        this.lightDash = lightDash;
        this.lightAirslash = lightAirslash;
        this.heavyDash = heavyDash;
        this.heavyAirslash = heavyAirslash;
    }

    public ComboNode getLightNode(){
        return this.lightNode;
    }

    public ComboNode getHeavyNode(){
        return this.heavyNode;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getLightDash(){
        return this.lightDash;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getLightAirslash(){
        return this.lightAirslash;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getHeavyDash(){
        return this.heavyDash;
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getHeavyAirslash(){
        return this.heavyAirslash;
    }


    
}
