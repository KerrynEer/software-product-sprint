package com.google.sps;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    
    Collection<TimeRange> timeSlots = new ArrayList<TimeRange>();

    Long requestedDuration = request.getDuration();
    if (requestedDuration > TimeRange.WHOLE_DAY.duration()) {
      return timeSlots;
    }

    // 1. Filter out events that at least 1 attendee needs to attend
    Collection<Event> clashedEvents = new ArrayList<Event>();
    Collection<String> attendees = request.getAttendees();

    for (Event event: events) {
      if (hasClash(event, attendees)) {
        clashedEvents.add(event);
      }
    }

    // 2. Get the remaining time slots without clash
    timeSlots.add(TimeRange.WHOLE_DAY);
    
    for (Event event: clashedEvents) {
      TimeRange eventTimeSlot = event.getWhen();

      //iterate through each timeslot and update if clash
      Collection<TimeRange> updatedTimeSlots = new ArrayList<TimeRange>();
 
      for (TimeRange timeSlot: timeSlots) {
      
        if (timeSlot.overlaps(eventTimeSlot)) {
          int start = timeSlot.start();
          int end = timeSlot.end();
          int eventStart = eventTimeSlot.start();
          int eventEnd = eventTimeSlot.end();
          TimeRange newTimeSlot;

          if (start < eventStart) {
            newTimeSlot = TimeRange.fromStartEnd(start, eventStart, false);
            if (newTimeSlot.duration() >= requestedDuration) {
              updatedTimeSlots.add(newTimeSlot);
            }
          }

          if (end > eventEnd) {
            newTimeSlot = TimeRange.fromStartEnd(eventEnd, end, false);
            if (newTimeSlot.duration() >= requestedDuration) {
              updatedTimeSlots.add(newTimeSlot);
            }
          }

        } else {
          updatedTimeSlots.add(timeSlot);
        }
      }
  
      timeSlots = updatedTimeSlots;
    } 

    return timeSlots;
  }

  private Boolean hasClash(Event scheduled, Collection<String> attendees ) {
    Set<String> eventAttendees = scheduled.getAttendees();
  
    for (String attendee: attendees) {
      if (eventAttendees.contains(attendee)) {
        return true;
      } 
    }
    return false;
  }
}
