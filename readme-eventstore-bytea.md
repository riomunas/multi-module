## oid untuk axon di rubah jadi bytea supaya readable dan kalau pindah database bisa kebawa

1. matikan applikasi/services
2. eksekusi script berikut :

```sql
-- token_entry
ALTER TABLE token_entry ADD COLUMN token_bytea BYTEA;
UPDATE token_entry SET token_bytea = lo_get(token);
ALTER TABLE token_entry  DROP COLUMN token;
ALTER TABLE token_entry RENAME COLUMN token_bytea to token;

-- saga_entry
ALTER TABLE saga_entry ADD COLUMN serialized_saga_bytea BYTEA;
UPDATE saga_entry SET serialized_saga_bytea = lo_get(serialized_saga);
ALTER TABLE saga_entry  DROP COLUMN serialized_saga;
ALTER TABLE saga_entry RENAME COLUMN serialized_saga_bytea to serialized_saga;

-- domain_event_entry
ALTER TABLE domain_event_entry ADD COLUMN payload_bytea BYTEA;
UPDATE domain_event_entry SET payload_bytea = lo_get(payload);
ALTER TABLE domain_event_entry  DROP COLUMN payload;
ALTER TABLE domain_event_entry RENAME COLUMN payload_bytea to payload;

ALTER TABLE domain_event_entry ADD COLUMN meta_data_bytea BYTEA;
UPDATE domain_event_entry SET meta_data_bytea = lo_get(meta_data);
ALTER TABLE domain_event_entry  DROP COLUMN meta_data;
ALTER TABLE domain_event_entry RENAME COLUMN meta_data_bytea to meta_data;

-- snapshot_event_entry
ALTER TABLE snapshot_event_entry ADD COLUMN payload_bytea BYTEA;
UPDATE snapshot_event_entry SET payload_bytea = lo_get(payload);
ALTER TABLE snapshot_event_entry  DROP COLUMN payload;
ALTER TABLE snapshot_event_entry RENAME COLUMN payload_bytea to payload;

ALTER TABLE snapshot_event_entry ADD COLUMN meta_data_bytea BYTEA;
UPDATE snapshot_event_entry SET meta_data_bytea = lo_get(meta_data);
ALTER TABLE snapshot_event_entry  DROP COLUMN meta_data;
ALTER TABLE snapshot_event_entry RENAME COLUMN meta_data_bytea to meta_data;

-- dead_letter_entry
ALTER TABLE dead_letter_entry ADD COLUMN payload_bytea BYTEA;
UPDATE dead_letter_entry SET payload_bytea = lo_get(payload);
ALTER TABLE dead_letter_entry  DROP COLUMN payload;
ALTER TABLE dead_letter_entry RENAME COLUMN payload_bytea to payload;

ALTER TABLE dead_letter_entry ADD COLUMN meta_data_bytea BYTEA;
UPDATE dead_letter_entry SET meta_data_bytea = lo_get(meta_data);
ALTER TABLE dead_letter_entry  DROP COLUMN meta_data;
ALTER TABLE dead_letter_entry RENAME COLUMN meta_data_bytea to meta_data;

ALTER TABLE dead_letter_entry ADD COLUMN token_bytea BYTEA;
UPDATE dead_letter_entry SET token_bytea = lo_get(token);
ALTER TABLE dead_letter_entry  DROP COLUMN token;
ALTER TABLE dead_letter_entry RENAME COLUMN token_bytea to token;

ALTER TABLE dead_letter_entry ADD COLUMN diagnostics_bytea BYTEA;
UPDATE dead_letter_entry SET diagnostics_bytea = lo_get(diagnostics);
ALTER TABLE dead_letter_entry  DROP COLUMN diagnostics;
ALTER TABLE dead_letter_entry RENAME COLUMN diagnostics_bytea to diagnostics;
```
3. Buat Class NoToastPostgresSQLDialect
```java

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;
import org.hibernate.type.descriptor.jdbc.spi.JdbcTypeRegistry;

import java.sql.Types;

public class NoToastPostgresSQLDialect extends PostgreSQLDialect {
  public NoToastPostgresSQLDialect(){
    super(DatabaseVersion.make(9, 5));
  }

  @Override
  protected String columnType(int sqlTypeCode) {
    if (sqlTypeCode == SqlTypes.BLOB){
      return "jsonb";
    }
    return super.columnType(sqlTypeCode);
  }

  @Override
  protected String castType(int sqlTypeCode) {
    if (sqlTypeCode == SqlTypes.BLOB){
      return "jsonb";
    }
    return super.castType(sqlTypeCode);
  }

  @Override
  public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
    super.contributeTypes(typeContributions, serviceRegistry);
    JdbcTypeRegistry jdbcTypeRegistry = typeContributions.getTypeConfiguration().getJdbcTypeRegistry();
    jdbcTypeRegistry.addDescriptor(Types.BLOB, BinaryJdbcType.INSTANCE);
  }
}
```
4. Di application.yaml

```yaml
spring.jpa.database-platform: org.example.config.NoToastPostgresSQLDialect
```
5. buat scrip orm.xml di scr/main/resources/META-INF
```xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings xmlns="http://java.sun.com/xml/ns/persistence/orm" version="2.0">
  <entity class="org.axonframework.eventhandling.tokenstore.jpa.TokenEntry">
    <attribute-override name="token">
      <column name="token" column-definition="BYTEA"/>
    </attribute-override>
  </entity>

  <entity class="org.axonframework.eventsourcing.eventstore.jpa.SnapshotEventEntry">
    <attribute-override name="payload">
      <column name="payload" column-definition="BYTEA"/>
    </attribute-override>
    <attribute-override name="metaData">
      <column name="metaData" column-definition="BYTEA"/>
    </attribute-override>
  </entity>

  <entity class="org.axonframework.modelling.saga.repository.jpa.SagaEntry">
    <attribute-override name="serializedSaga">
      <column name="serializedSaga" column-definition="BYTEA"/>
    </attribute-override>
  </entity>

  <entity class="org.axonframework.eventsourcing.eventstore.jpa.DomainEventEntry">
    <attribute-override name="payload">
      <column name="payload" column-definition="BYTEA"/>
    </attribute-override>
    <attribute-override name="metaData">
      <column name="metaData" column-definition="BYTEA"/>
    </attribute-override>
  </entity>

  <entity class="org.axonframework.eventhandling.deadletter.jpa.DeadLetterEntry">
    <attribute-override name="payload">
      <column name="payload" column-definition="BYTEA"/>
    </attribute-override>
    <attribute-override name="metaData">
      <column name="metaData" column-definition="BYTEA"/>
    </attribute-override>
    <attribute-override name="token">
      <column name="token" column-definition="BYTEA"/>
    </attribute-override>
    <attribute-override name="diagnostics">
      <column name="diagnostics" column-definition="BYTEA"/>
    </attribute-override>
  </entity>
</entity-mappings>
```
6. run projectnya