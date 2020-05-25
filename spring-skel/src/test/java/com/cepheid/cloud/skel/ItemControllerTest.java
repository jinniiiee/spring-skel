package com.cepheid.cloud.skel;

import java.util.Collection;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cepheid.cloud.skel.model.State;
import com.cepheid.cloud.skel.model.payload.DescriptionRequest;
import com.cepheid.cloud.skel.model.payload.ItemRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import com.cepheid.cloud.skel.model.Item;

@RunWith(SpringRunner.class)
public class ItemControllerTest extends TestBase {

  private final String API_PATH = "/app/api/1.0/items";

  @Test
  public void testGetAllItems() {
    createItem("Hobbo", State.UNDEFINED);
    createItem("Hollo", State.INVALID);
    Collection<Item> items = getBuilder(API_PATH).get(new GenericType<Collection<Item>>() {
    });
    assertNotNull(items);
    assertTrue(items.size() >= 2);
  }

  @Test
  public void testGetItemById() {
    Response itemCreated = createItem("Hotto", State.UNDEFINED);
    Item item = getBuilder(API_PATH + "/" + itemCreated.getHeaderString("Id")).get(Item.class);
    assertNotNull(item);
  }

  @Test
  public void testGetItemByName() {
    createItem("Hokko", State.UNDEFINED);
    Collection<Item> items = getBuilder(API_PATH + "?name=" + "Hokko").get(new GenericType<Collection<Item>>() {
    });
    assertNotNull(items);
    assertTrue(items.size() > 0);
  }

  @Test
  public void testGetItemBySate() {
    createItem("Horro", State.UNDEFINED);
    Collection<Item> items = getBuilder(API_PATH + "?state=" + State.UNDEFINED).get(new GenericType<Collection<Item>>() {
    });
    assertNotNull(items);
    assertTrue(items.size() > 0);
  }

  @Test
  public void testGetItemsWithNameAndState() {
    createItem("Hoppo", State.UNDEFINED);
    Collection<Item> items = getBuilder(API_PATH + "?name=" + "Hoppo" + "&state=" + State.UNDEFINED).
            get(new GenericType<Collection<Item>>() {
    });
    assertNotNull(items);
    assertTrue(items.size() > 0);
  }

  @Test
  public void testCreate() {
    Response itemCreated = createItem("Hoddo", State.UNDEFINED);
    assertEquals(Response.Status.CREATED.getStatusCode(), itemCreated.getStatus());
    assertNotNull(itemCreated.getHeaderString("Id"));
    Item item = getBuilder(API_PATH + "/" + itemCreated.getHeaderString("Id")).get(Item.class);
    assertNotNull(item);
    assertNotNull(item.getId());
    assertNotNull(item.getDescriptions());
    assertFalse(item.getDescriptions().isEmpty());
  }

  @Test
  public void testItemUpdate() {
    Response itemCreated = createItem("hommo", State.UNDEFINED);
    ItemRequest itemRequest = buildItemRequest("hommo", State.VALID.name());
    Response itemUpdated = getBuilder(API_PATH + "/" + itemCreated.getHeaderString("Id")).
            put(Entity.entity(itemRequest, MediaType.APPLICATION_JSON));
    assertEquals(Response.Status.OK.getStatusCode(), itemUpdated.getStatus());
    Item item = getBuilder(API_PATH + "/" + itemCreated.getHeaderString("Id")).get(Item.class);
    assertNotNull(item);
    assertFalse(item.getDescriptions().isEmpty());
    assertEquals(State.VALID, item.getState());
  }

  @Test
  public void testDelete() {
    Response itemCreated = createItem("Hojjo", State.UNDEFINED);
    Response itemDeleted = getBuilder(API_PATH + "/" + itemCreated.getHeaderString("Id")).delete();
    assertEquals(Response.Status.OK.getStatusCode(), itemDeleted.getStatus());
    Item item = getBuilder(API_PATH + "/" + itemCreated.getHeaderString("Id")).get(Item.class);
    assertNull(item);
  }


  private Response createItem(String name, State state) {
    ItemRequest itemRequest = buildItemRequest(name, state.name());
    return getBuilder(API_PATH).post(Entity.entity(itemRequest, MediaType.APPLICATION_JSON));
  }

  private ItemRequest buildItemRequest(String name, String state) {
    DescriptionRequest descReq1 = new DescriptionRequest();
    DescriptionRequest descReq2 = new DescriptionRequest();
    descReq1.setSummary("Description is a long word.");
    descReq1.setType("LONG");
    descReq2.setSummary("Desc is a short word.");
    descReq2.setType("SHORT");

    ItemRequest itemRequest = new ItemRequest();
    itemRequest.setDescriptions(Set.of(descReq1, descReq2));
    itemRequest.setName(name);
    itemRequest.setState(state);
    return itemRequest;
  }
}
