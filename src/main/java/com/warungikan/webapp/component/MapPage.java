package com.warungikan.webapp.component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.model.RSAddName;
import com.warungikan.webapp.util.Constant;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;

public class MapPage extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1630397536461568535L;
	private static final String DEFAULT_LOCATION = "jakarta selatan";
	private GoogleMap addressMap;
	private AutocompleteField addressText;
	private RSAddName result = null;
	private boolean isValidationRequired;
	private LatLon point;
	public MapPage(String caption, boolean isValidationRequired, LatLon point){
		this.isValidationRequired = isValidationRequired;
		setCaption(caption);
		this.point = point;
		setWidth(630, Unit.PIXELS);
		addressText = createCompanyAutoComplete();
		addressText.addValidator(new StringLengthValidator("Koordinat/alamat via map diperlukan", 3, 100, false));
		addressText.setPrimaryStyleName("v-textfield v-widget small v-textfield-small");
		addressMap = new GoogleMap(Constant.GMAP_API_KEY, null, "english");

		addressMap.setSizeFull();
		addressMap.setMinZoom(4);
		addressMap.setMaxZoom(16);
		addressMap.setZoom(16);
		
		if(point != null){
			addressMap.addMarker("My location", point, true, null);
			addressMap.setCenter(point);
		}
		
		addComponent(addressText);
		addComponent(addressMap);
		
		setExpandRatio(addressText, 0.0f);
		setExpandRatio(addressMap, 1.0f);
		setSpacing(true);
		
		createDefaultPoint();
	}
	
	private void createDefaultPoint() {
		RSAddName rs = getLatitudeLongitude(DEFAULT_LOCATION);
		LatLon point = new LatLon(Double.parseDouble(rs.getLatitude()), Double.parseDouble(rs.getLongitude()));
		addressMap.addMarker("Choose your address", point, true, null);
		addressMap.setSizeFull();
	}

	private AutocompleteField createCompanyAutoComplete() {
		AutocompleteField<String> field = new AutocompleteField<>();
		field.setWidth(100, Unit.PERCENTAGE);
		field.setQueryListener(new AutocompleteQueryListener<String>() {

			@Override
			public void handleUserQuery(AutocompleteField<String> arg0, String arg1) {

				List<String> rsult = MapPage.this.getGoogleAutocomplete(arg1);

				for(String s:rsult) {
					arg0.addSuggestion(s, s);
				}
			}
		});
		field.setSuggestionPickedListener(e -> {
			RSAddName result = getLatitudeLongitude(e);
			result.setType("s");
			String latitude = result.getLatitude();
			String longtitude = result.getLongitude();
			String company = result.getCompanyName();
			
			LatLon point = new LatLon(Double.parseDouble(latitude), Double.parseDouble(longtitude));
			addressMap.addMarker("DRAGGABLE: "+company, point, true, null);
			addressMap.addMarkerDragListener(new MarkerDragListener() {
				
				@Override
				public void markerDragged(GoogleMapMarker draggedMarker, LatLon oldPosition) {
					String lat = String.valueOf(draggedMarker.getPosition().getLat());
					String lon = String.valueOf(draggedMarker.getPosition().getLon());
					if(MapPage.this.result == null){
						MapPage.this.result = new RSAddName();
					}
					result.setLatitude(lat);
					result.setLongitude(lon);
				}
			});
			addressMap.setCenter(point);
			MapPage.this.result = result;
		});
		return field;
	}
	
	public RSAddName getLatitudeLongitude(String select) {

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		RSAddName addResult = new RSAddName();
		try {
			StringBuilder sb = new StringBuilder(Constant.PLACES_API_GEOCODE + Constant.OUT_JSON);
			sb.append("?sensor=false&key=" + Constant.API_KEY);
			sb.append("&address=" + URLEncoder.encode(select, "utf8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			System.out.println("Error processing Places API URL");
			e.printStackTrace();

		} catch (IOException e) {
			System.out.println("Error processing Places API URL");
			e.printStackTrace();

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());			
			JSONObject res = jsonObj.getJSONArray("results").getJSONObject(0);
			JSONObject locationObj = res.getJSONObject("geometry").getJSONObject("location");			
			String longitude = locationObj.get("lng").toString();
			String latitude = locationObj.get("lat").toString();
			String address = "";
			String street = "";
			String countryName = "";
			String countryId = "";
			String city = "";
			JSONArray addrresComArray = res.getJSONArray("address_components");


			for (int i = 0; i < addrresComArray.length(); i++) {

				JSONObject addComObject = (JSONObject) addrresComArray.get(i);
				JSONArray typeArray =  (JSONArray) addComObject.get("types");
				for (int k = 0; k < typeArray.length(); k++) {
					String type = typeArray.getString(k);
					if(type.equals("administrative_area_level_1") ){
						city = addComObject.getString("long_name").toString();
					}
					if(type.equals("administrative_area_level_1") && city.equals("")) {
						city = addComObject.getString("long_name").toString();
					}
					if(type.equals("administrative_area_level_3") && city.equals("")){
						city = addComObject.getString("short_name").toString();
					}
					if(type.equals("country") ){
						countryName = addComObject.getString("long_name").toString();
						countryId = addComObject.getString("short_name").toString();
					}
					if(type.equals("route") ){
						street = addComObject.getString("long_name").toString();
					}
					if(type.equals("administrative_area_level_4") && street.equals("")){
						street = addComObject.getString("long_name").toString();
					}
					if(address.equals("")){
						String[] formatAddress = res.getString("formatted_address").split(",");;
						int addressSize = formatAddress.length;
						for(int counter = 0; counter < addressSize; counter++){
							address = address.concat(formatAddress[counter]).concat(",");
							address = address.substring(0, address.length() - 1);
						}
					}
				}
			}
			addResult.setCompanyName(select).setCity(city).setCountry(countryName).
			setLatitude(latitude).setLongitude(longitude).setStreet(street).setType("s");

		} catch (JSONException e) {
			//			System.out.println("Error processing Placses API URL");
			e.printStackTrace();
		}

		return addResult;
	}
	
	public List<String> getGoogleAutocomplete(String match) {
		String jsonResults = getGoogleAutoComplete(match);

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults);

			if (jsonObj.get("status").equals("ZERO_RESULTS") && match.contains(",")) {
				String[] arrayMatch = match.split(",");
				for (int i = 0; i<arrayMatch.length; i++) {
					jsonResults = getGoogleAutoComplete(arrayMatch[i]);
					jsonObj = new JSONObject(jsonResults);
					JSONArray jsonArray = jsonObj.getJSONArray("predictions");
					if (jsonObj.get("status").equals("OK") && jsonArray.length() > 0) {
						break;
					}
				}

			}

			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			List googleList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				googleList.add(predsJsonArray.getJSONObject(i).getString("description"));
			}
			return googleList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}			
	}
	
	public String getGoogleAutoComplete(String match) {
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(Constant.PLACES_API_BASE + Constant.TYPE_AUTOCOMPLETE + Constant.OUT_JSON);
			sb.append("?sensor=false&key=" + Constant.API_KEY);
			sb.append("&input=" + URLEncoder.encode(match, "utf8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return jsonResults.toString();
	}

	public RSAddName getResult() {
		return result;
	}

	public void validate(){
		if(isValidationRequired && result == null){
			addressText.validate();
		}
	}

	public void setCoordinate(String user, Double latitude, Double longitude) {
		
		LatLon point = new LatLon(latitude, longitude);
		addressMap.addMarker("DRAGGABLE: "+user, point, true, null);
		if(result == null){
			result = new RSAddName();
		}
		result.setLatitude(String.valueOf(latitude)).setLongitude(String.valueOf(longitude));
	}
	
	
}
