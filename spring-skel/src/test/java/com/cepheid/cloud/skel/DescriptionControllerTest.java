package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.State;
import com.cepheid.cloud.skel.model.Type;
import com.cepheid.cloud.skel.model.payload.DescriptionRequest;
import com.cepheid.cloud.skel.model.payload.ItemRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class DescriptionControllerTest extends TestBase {

    private final String API_PATH = "/app/api/1.0/items";
    private final String DESC_API_PATH = API_PATH +"/descriptions";

    private final String LONG_SUMMARY = "Description is a long word.";
    private final String SHORT_SUMMARY = "Desc is a short word.";

    @Test
    public void testGetAllDescriptions() {
        createItemAdd2Descriptions();
        Collection<Description> descriptions = getBuilder(DESC_API_PATH).get(new GenericType<Collection<Description>>() {
        });
        assertNotNull(descriptions);
        assertTrue(descriptions.size() >= 2);
    }

    @Test
    public void testGetDescriptionById() {
        Description desc = getDescriptionAfterCreate("Hobbo", State.UNDEFINED);
        Description description = getBuilder(DESC_API_PATH + "/" +  desc.getId() ).get(Description.class);
        assertNotNull(description);
    }
    @Test
    public void testAddDescriptionOnItem() {
        Response itemCreated = createItemWithoutDescription("Hollo", State.UNDEFINED);
        Response descriptionAdded = addDescriptionOnItem(LONG_SUMMARY, Type.LONG, itemCreated.getHeaderString("Id"));
        assertEquals(Response.Status.CREATED.getStatusCode(), descriptionAdded.getStatus());
        Description description = getBuilder(DESC_API_PATH + "/" + descriptionAdded.getHeaderString("Id")).
                get(Description.class);
        assertNotNull(description);
    }
    @Test
    public void testUpdateDescription() {
        Description descriptionCreated = getDescriptionAfterCreate("Hokko", State.UNDEFINED);
        Response descriptionUpdated = getBuilder(DESC_API_PATH + "/" + descriptionCreated.getId()).put(Entity.entity(
                buildDescriptionRequest("Description is a long word anyways.", "LONG"),
                MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.OK.getStatusCode(), descriptionUpdated.getStatus());

        Description description = getBuilder(DESC_API_PATH + "/" + descriptionCreated.getId()).get(Description.class);
        assertNotNull(description);
        assertEquals("Description is a long word anyways.", description.getSummary());
    }

    private Description getDescriptionAfterCreate(String name, State state) {
        Response itemCreated = createItemWithDescription(name, state);
        Item item = getBuilder(API_PATH + "/" + itemCreated.getHeaderString("Id")).get(Item.class);
        assertNotNull(item);
        assertNotNull(item.getDescriptions());
        assertFalse(item.getDescriptions().isEmpty());
        Description descriptionCreated = item.getDescriptions().iterator().next();
        assertNotNull(descriptionCreated);
        return descriptionCreated;
    }

    private Response createItemAdd2Descriptions() {
        Response itemCreated = createItemWithoutDescription("Hoppo", State.UNDEFINED);
        addDescriptionOnItem(LONG_SUMMARY, Type.LONG, itemCreated.getHeaderString("Id"));
        addDescriptionOnItem(SHORT_SUMMARY, Type.SHORT, itemCreated.getHeaderString("Id"));
        return itemCreated;
    }

    private Response addDescriptionOnItem(String summary, Type type, String itemId) {
        DescriptionRequest descriptionRequest = buildDescriptionRequest(summary, type.name());
        return getBuilder(API_PATH + "/" + itemId + "/descriptions").post(Entity.entity(descriptionRequest, MediaType.APPLICATION_JSON));
    }

    private DescriptionRequest buildDescriptionRequest(String summary, String name) {
        DescriptionRequest descriptionRequest = new DescriptionRequest();
        descriptionRequest.setSummary(summary);
        descriptionRequest.setType(name);
        return descriptionRequest;
    }

    private Response createItemWithoutDescription(String name, State state) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName(name);
        itemRequest.setState(state.name());
        return getBuilder(API_PATH).post(Entity.entity(itemRequest, MediaType.APPLICATION_JSON));
    }

    private Response createItemWithDescription(String name, State state) {
        ItemRequest itemRequest = buildItemRequest(name, state.name());
        return getBuilder(API_PATH).post(Entity.entity(itemRequest, MediaType.APPLICATION_JSON));
    }

    private ItemRequest buildItemRequest(String name, String state) {
        DescriptionRequest descReq1 = buildDescriptionRequest("Description is a long word.", "LONG");
        DescriptionRequest descReq2 = buildDescriptionRequest("Desc is a short word.", "SHORT");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescriptions(Set.of(descReq1, descReq2));
        itemRequest.setName(name);
        itemRequest.setState(state);
        return itemRequest;
    }
}
