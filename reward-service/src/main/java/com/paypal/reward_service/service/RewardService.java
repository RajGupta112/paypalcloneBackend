package com.paypal.reward_service.service;

import com.paypal.reward_service.entity.Reward;
import org.w3c.dom.stylesheets.LinkStyle;

import java.awt.*;
import java.util.List;

public interface RewardService {

    Reward sentReward(Reward reward);

      List<Reward> getRewardByUserId(Long userId);
}
