package com.pzen.server.utils;
import java.lang.reflect.Field;

public class EntityHelper {

    /**
     * 将 DTO 对象的属性值映射到实体对象的属性值
     *
     * @param dto DTO 对象
     * @param entity 实体对象
     * @param <D> DTO 类型
     * @param <E> 实体类型
     */
    public static <D, E> void convertDtoToEntity(D dto, E entity) {
        if (dto == null || entity == null) {
            return;
        }
        Class<?> dtoClass = dto.getClass();
        Class<?> entityClass = entity.getClass();
        for (Field dtoField : dtoClass.getDeclaredFields()) {
            dtoField.setAccessible(true);
            try {
                Object dtoValue = dtoField.get(dto);
                // 过滤掉 null 值和空字符串
//                if (dtoValue == null || (dtoValue instanceof String && ((String) dtoValue).isEmpty())) {
                if (dtoValue == null) {
                    continue;
                }
                Field entityField = entityClass.getDeclaredField(dtoField.getName());
                entityField.setAccessible(true);
                // 确保字段类型匹配
                if (entityField.getType().isAssignableFrom(dtoField.getType())) {
                    entityField.set(entity, dtoValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }








}
