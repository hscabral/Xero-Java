package com.xero.api.client;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;


import com.xero.api.XeroApiException;
import com.xero.api.ApiClient;
import com.xero.example.CustomJsonConfig;

import com.xero.api.client.*;
import com.xero.models.accounting.*;

import com.xero.example.SampleData;

import org.threeten.bp.*;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class AccountingApiItemsTest {

	CustomJsonConfig config;
	ApiClient apiClientForAccounting; 
	AccountingApi api; 

    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null);
		api = new AccountingApi(config);
		api.setApiClient(apiClientForAccounting);
		api.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());

        // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
        if (setUpIsDone) {
            return;
        }

        try {
            System.out.println("Sleep for 30 seconds");
            Thread.sleep(30000);
        } catch(InterruptedException e) {
            System.out.println(e);
        }
        // do the setup
        setUpIsDone = true;
	}

	public void tearDown() {
		api = null;
		apiClientForAccounting = null;
	}

    @Test
    public void createItemTest() throws IOException {
        System.out.println("@Test - createItem");
        Items items = null;
        Items response = api.createItem(items);

        assertThat(response.getItems().get(0).getCode(), is(equalTo("abc65591")));
        assertThat(response.getItems().get(0).getName(), is(equalTo("Hello11350")));
        assertThat(response.getItems().get(0).getIsSold(), is(equalTo(true)));
        assertThat(response.getItems().get(0).getIsPurchased(), is(equalTo(true)));
        assertThat(response.getItems().get(0).getDescription(), is(equalTo("foobar")));
        assertThat(response.getItems().get(0).getIsTrackedAsInventory(), is(equalTo(false)));
        assertThat(response.getItems().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T12:17:51.707-07:00"))));  
        assertThat(response.getItems().get(0).getItemID(), is(equalTo(UUID.fromString("a4544d51-48f6-441f-a623-99ecbced6ab7"))));
        assertThat(response.getItems().get(0).getValidationErrors().get(0).getMessage(), is(equalTo("Price List Item with Code 'abc' already exists")));
        //System.out.println(response.getItems().get(0).toString());
    }
    
    @Test
    public void createItemHistoryTest() throws IOException {
        System.out.println("@Test - createItemHistory - not implemented");
        UUID itemID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords historyRecords = null;
        //HistoryRecords response = api.createItemHistory(itemID, historyRecords);
        // TODO: test validations
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }

    @Test
    public void getItemTest() throws IOException {
        System.out.println("@Test - getItem");
        UUID itemID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Items response = api.getItem(itemID);

        assertThat(response.getItems().get(0).getCode(), is(equalTo("123")));
        assertThat(response.getItems().get(0).getInventoryAssetAccountCode(), is(equalTo("630")));
        assertThat(response.getItems().get(0).getName(), is(equalTo("Guitars")));
        assertThat(response.getItems().get(0).getIsSold(), is(equalTo(true)));
        assertThat(response.getItems().get(0).getIsPurchased(), is(equalTo(true)));
        assertThat(response.getItems().get(0).getDescription(), is(equalTo("Guitars Fender Strat")));
        assertThat(response.getItems().get(0).getPurchaseDescription(), is(equalTo("Brand new Fender Strats")));
        assertThat(response.getItems().get(0).getPurchaseDetails().getUnitPrice(), is(equalTo(2500.0)));
        assertThat(response.getItems().get(0).getPurchaseDetails().getCoGSAccountCode(), is(equalTo("310")));
        assertThat(response.getItems().get(0).getPurchaseDetails().getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getItems().get(0).getSalesDetails().getUnitPrice(), is(equalTo(5000.0)));
        assertThat(response.getItems().get(0).getSalesDetails().getAccountCode(), is(equalTo("200")));
        assertThat(response.getItems().get(0).getSalesDetails().getTaxType(), is(equalTo("OUTPUT2")));
        assertThat(response.getItems().get(0).getIsTrackedAsInventory(), is(equalTo(true)));
        assertThat(response.getItems().get(0).getTotalCostPool(), is(equalTo(25000.0)));
        assertThat(response.getItems().get(0).getTotalCostPool().toString(), is(equalTo("25000.0")));
        assertThat(response.getItems().get(0).getQuantityOnHand(), is(equalTo(10.0)));
        assertThat(response.getItems().get(0).getQuantityOnHand().toString(), is(equalTo("10.0")));
        assertThat(response.getItems().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T12:41:49.387-07:00"))));  
        assertThat(response.getItems().get(0).getItemID(), is(equalTo(UUID.fromString("c8c54d65-f3f2-452d-926e-bf450b12fb07"))));
        //System.out.println(response.getItems().get(0).toString());
    }

    @Test
    public void getItemHistoryTest() throws IOException {
        System.out.println("@Test - getItemHistory");
        UUID itemID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords response = api.getItemHistory(itemID);

        assertThat(response.getHistoryRecords().get(0).getUser(), is(equalTo("Sidney Maestre")));       
        assertThat(response.getHistoryRecords().get(0).getChanges(), is(equalTo("Created")));     
        assertThat(response.getHistoryRecords().get(0).getDetails(), is(equalTo("Item 123 - Guitars created.")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-07T09:57:56-08:00"))));  
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }
   
    @Test
    public void getItemsTest() throws IOException {
        System.out.println("@Test - getItems");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Integer unitdp = null;
        Items response = api.getItems(ifModifiedSince, where, order, unitdp);

        assertThat(response.getItems().get(0).getCode(), is(equalTo("123")));
        assertThat(response.getItems().get(0).getName(), is(equalTo("Guitars")));
        assertThat(response.getItems().get(0).getIsSold(), is(equalTo(true)));
        assertThat(response.getItems().get(0).getIsPurchased(), is(equalTo(false)));
        assertThat(response.getItems().get(0).getDescription(), is(equalTo("Guitars Fender Strat")));
        assertThat(response.getItems().get(0).getSalesDetails().getUnitPrice(), is(equalTo(5000.0)));
        assertThat(response.getItems().get(0).getSalesDetails().getAccountCode(), is(equalTo("200")));
        assertThat(response.getItems().get(0).getSalesDetails().getTaxType(), is(equalTo("OUTPUT2")));
        assertThat(response.getItems().get(0).getIsTrackedAsInventory(), is(equalTo(false)));
        assertThat(response.getItems().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-07T09:57:56.267-08:00"))));  
        assertThat(response.getItems().get(0).getItemID(), is(equalTo(UUID.fromString("c8c54d65-f3f2-452d-926e-bf450b12fb07"))));
        //System.out.println(response.getItems().get(0).toString());
    }

    @Test
    public void updateItemTest() throws IOException {
        System.out.println("@Test - updateItem");
        UUID itemID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Items items = null;
        Items response = api.updateItem(itemID, items);

        assertThat(response.getItems().get(0).getCode(), is(equalTo("abc38306")));
        assertThat(response.getItems().get(0).getName(), is(equalTo("Hello8746")));
        assertThat(response.getItems().get(0).getIsSold(), is(equalTo(true)));
        assertThat(response.getItems().get(0).getIsPurchased(), is(equalTo(true)));
        assertThat(response.getItems().get(0).getDescription(), is(equalTo("Hello Xero")));
        assertThat(response.getItems().get(0).getIsTrackedAsInventory(), is(equalTo(false)));
        assertThat(response.getItems().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T12:29:18.924-07:00"))));  
        assertThat(response.getItems().get(0).getItemID(), is(equalTo(UUID.fromString("a7e87086-e0ae-4df2-83d7-e26e9a6b7786"))));
        //System.out.println(response.getItems().get(0).toString());
    }

    @Test
    public void deleteItemTest() throws IOException {
        System.out.println("@Test - deleteItem");
        UUID itemID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        api.deleteItem(itemID);
    }
}
