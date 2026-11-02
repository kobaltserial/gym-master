import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(13, thursdaySessions.get(0).getTimeOfDay().getHours());
        assertEquals(20, thursdaySessions.get(1).getTimeOfDay().getHours());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> listForTest =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, listForTest.size());
        listForTest =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertEquals(0, listForTest.size());
    }

    @Test
    void testMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Group group2 = new Group("Акробатика для детей 2", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильев 2", "Николай 2", "Сергеевич 2");
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(secondTrainingSession);

        assertEquals(2, timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());
    }

    @Test
    void testGetTrainingSessionsForWrongDay() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetable = new Timetable();
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession firstSession = new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstSession);

        Group group2 = new Group("Акробатика для детей 2", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильев 2", "Николай 2", "Сергеевич 2");

        TrainingSession secondSession = new TrainingSession(
                group2, coach2, DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(secondSession);

        Group group3 = new Group("Акробатика для детей 3", Age.CHILD, 60);
        Coach coachCopy = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession thirdSession = new TrainingSession(
                group3, coachCopy, DayOfWeek.TUESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(thirdSession);

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());

        assertEquals(2, result.get(0).getValue());
        assertEquals(1, result.get(1).getValue());

        assertEquals(coach, result.get(0).getKey());
        assertEquals(coach2, result.get(1).getKey());
    }

    @Test
    void testGetCountByCoachesWithEqualCounts() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Group group2 = new Group("Акробатика для детей 2", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильев 2", "Николай 2", "Сергеевич 2");
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(secondTrainingSession);

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getValue());
        assertEquals(1, result.get(1).getValue());
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();
        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();
        assertEquals(0, result.size());
    }
}
