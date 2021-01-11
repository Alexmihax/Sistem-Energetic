package entities;

import input.EntityInput;

public interface EntityFactory<T> {
    /**
     *
     * @param inputData for the entity constructor
     * @param type of the entity
     * @return an entity of the specified type
     */
    T create(EntityInput inputData, String type);
}
