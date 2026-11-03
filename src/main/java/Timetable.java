
import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    private final HashMap<Coach, Integer> coachesCounter = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        TreeMap<TimeOfDay, List<TrainingSession>> dailySchedule =
                timetable.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new TreeMap<>());

        List<TrainingSession> sessions =
                dailySchedule.computeIfAbsent(trainingSession.getTimeOfDay(), k -> new ArrayList<>());

        sessions.add(trainingSession);

        Coach currentCoach = trainingSession.getCoach();
        coachesCounter.put(
                currentCoach,
                coachesCounter.getOrDefault(currentCoach, 0) + 1
        );
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);

        if (trainingsForDay == null) {
            return new TreeMap<>();
        }

        return trainingsForDay;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> schedule = timetable.get(dayOfWeek);

        if (schedule == null) {
            return List.of();
        }

        List<TrainingSession> trainingSessions = schedule.get(timeOfDay);

        if (trainingSessions == null) {
            return List.of();
        }

        return trainingSessions;
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        List<Map.Entry<Coach, Integer>> counterOfTrainingsList = new ArrayList<>();

        for (Coach coach : coachesCounter.keySet()) {
            Map.Entry<Coach, Integer> counterOfTrainings =
                    new AbstractMap.SimpleEntry<>(coach, coachesCounter.get(coach));

            counterOfTrainingsList.add(counterOfTrainings);
        }

        return counterOfTrainingsList;
    }
}
