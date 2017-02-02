package gcloud_wrap;

import com.google.cloud.datastore.*;
import com.google.datastore.v1.ArrayValue;
import com.google.datastore.v1.ArrayValueOrBuilder;

import java.util.Date;
import java.util.List;
import java.util.Set;


public class GCloudWrapper {

    public static Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    public static IncompleteKey entityKey(Datastore ds, String kind) {
        return ds.newKeyFactory().setKind(kind).newKey();
    }

    public static Key entityKey(Datastore ds, String kind, String name) {
        return ds.newKeyFactory().setKind(kind).newKey(name);
    }

    public static Key entityKeyLong(Datastore ds, String kind, Long id) {
        return ds.newKeyFactory().setKind(kind).newKey(id);
    }

//    //TODO fix this
//    public static Key entityKey(Datastore ds, IncompleteKey entityKey) {
//        return ds.add(FullEntity.newBuilder(entityKey).build()).getKey();
//    }

    //value fors
    //Boolean
    public static Value valueFor(boolean value) {
        return new BooleanValue(value);
    }

    //Blob
    public static Value valueFor(Blob value) {
        return new BlobValue(value);
    }

    //Date and time
    public static Value valueFor(Date value) {
        DateTime dt = DateTime.copyFrom(value);
        return new DateTimeValue(dt);
    }

    //Embedded entity
    public static Value valueFor(Entity value) {
        return new EntityValue(value);
    }

    //Floating-point number
    public static Value valueFor(Double value) {
        return new DoubleValue(value);
    }

    //Geographical point
    public static Value valueFor(LatLng value) {
        return new LatLngValue(value);
    }

    //Integer
    public static Value valueFor(Long value) {
        return new LongValue(value);
    }

    //Key
    public static Value valueFor(Key value) {
        return new KeyValue(value);
    }

    //Null
    public static Value valueFor() {
        return new NullValue();
    }

    //Text string
    public static Value valueFor(String value) {
        return new StringValue(value);
    }

    public static Value getValue(Entity entity, String name) {
        return entity.getValue(name);
    }

    public static Set getNames(Entity entity) {
        return entity.getNames();
    }

    //setKVs
    //Array
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, List value, boolean indexed) {
        // Exclude from indexes cannot be set on a list value
        return builder.set(key, value);
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, List value, boolean indexed) {
        // Exclude from indexes cannot be set on a list value
        return builder.set(key, value);
    }

    //Boolean
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, boolean value, boolean indexed) {
        return builder.set(key, BooleanValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, boolean value, boolean indexed) {
        return builder.set(key, BooleanValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    //Blob
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, Blob value, boolean indexed) {
        return builder.set(key, BlobValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, Blob value, boolean indexed) {
        return builder.set(key, BlobValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    //Date
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, Date value, boolean indexed) {
        DateTime dt = DateTime.copyFrom(value);
        return builder.set(key, DateTimeValue.newBuilder(dt).setExcludeFromIndexes(!indexed).build());
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, Date value, boolean indexed) {
        DateTime dt = DateTime.copyFrom(value);
        return builder.set(key, DateTimeValue.newBuilder(dt).setExcludeFromIndexes(!indexed).build());
    }

    //Embeded Entity
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, Entity value, boolean indexed) {
        return builder.set(key, EntityValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, Entity value, boolean indexed) {
        return builder.set(key, EntityValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    //Floating-point number
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, Double value, boolean indexed) {
        return builder.set(key, DoubleValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, Double value, boolean indexed) {
        return builder.set(key, DoubleValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    //Geographical point
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, LatLng value, boolean indexed) {
        return builder.set(key, LatLngValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, LatLng value, boolean indexed) {
        return builder.set(key, LatLngValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    //Integer
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, Long value, boolean indexed) {
        return builder.set(key, LongValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, Long value, boolean indexed) {
        return builder.set(key, LongValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    //Key
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, Key value, boolean indexed) {
        return builder.set(key, KeyValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, Key value, boolean indexed) {
        return builder.set(key, KeyValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    //Null
    public static BaseEntity.Builder setKVnull(Entity.Builder builder, String key, boolean indexed) {
        return builder.setNull(key);
    }

    public static BaseEntity.Builder setKVnull(FullEntity.Builder builder, String key, boolean indexed) {
        return builder.setNull(key);
    }

    //Text string
    public static BaseEntity.Builder setKV(Entity.Builder builder, String key, String value, boolean indexed) {
        return builder.set(key, StringValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    public static BaseEntity.Builder setKV(FullEntity.Builder builder, String key, String value, boolean indexed) {
        return builder.set(key, StringValue.newBuilder(value).setExcludeFromIndexes(!indexed).build());
    }

    public static Key getKey(Entity entity) {
        return entity.getKey();
    }

    public static Entity.Builder entityBuilder(Key entityKey) {
        return Entity.newBuilder(entityKey);
    }

    public static FullEntity.Builder entityBuilder(IncompleteKey entityKey) {
        return FullEntity.newBuilder(entityKey);
    }

    public static FullEntity build(FullEntity.Builder eb) {
        return eb.build();
    }

    public static Entity build(Entity.Builder eb) {
        return eb.build();
    }

    public static Entity getEntity(Datastore ds, Key entityKey) {
        return ds.get(entityKey);
    }

    public static Entity putEntity(Datastore ds, Entity entity) {
        return ds.put(entity);
    }

    public static Entity addEntity(Datastore ds, FullEntity entity) {
        return ds.add(entity);
    }

    //TODO add list of entities

    public static Entity updateEntity(Datastore ds, Entity entity) {
        ds.update(entity);
        return entity;
    }

    public static void deleteEntity(Datastore ds, Key entityKey) {
        ds.delete(entityKey);
    }
}
