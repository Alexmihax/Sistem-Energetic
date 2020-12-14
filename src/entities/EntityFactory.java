package entities;

import common.Constants;

public class EntityFactory {
    private EntityFactory instance = new EntityFactory();

    public EntityFactory getInstance() {
        return instance;
    }

//    public Entity getEntity(String entityType) {
//        return switch (entityType) {
//            case Constants.CONSUMERS -> {
//                // return new Consumer();
//            }
//            case Constants.DISTRIBUTORS -> {
//                // return new Distributor();
//            }
//            // default;
//        };
//        return null;
//    }
}
