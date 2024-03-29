package com.abstractionizer.electronicstore.storage.rdbms.mappers;

import com.abstractionizer.electronicstore.model.deal.DealVo;
import com.abstractionizer.electronicstore.storage.rdbms.entities.DealEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DealMapper extends BaseMapper<DealEntity> {
    int countByNameOrApplyOrder(String name, Integer applyOrder);

    List<DealVo> selectAllDealsInAscOrderByApplyOrder();
}
