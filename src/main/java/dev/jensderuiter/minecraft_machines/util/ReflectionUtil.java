package dev.jensderuiter.minecraft_machines.util;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.machine.Machine;
import dev.jensderuiter.minecraft_machines.machine.RegisterMachine;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtil {

    public static Set<Class> findAllMachines() {
        String packageName = "dev.jensderuiter.minecraft_machines.machine";
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }

    public static void registerMachineTypes() {
        Set<Class> machineClasses = findAllMachines();
        for (Class clazz : machineClasses) {
            RegisterMachine classAnnotation = (RegisterMachine) clazz.getAnnotation(RegisterMachine.class);
            if (classAnnotation != null) {
                try {
                    Method nameMethod = clazz.getDeclaredMethod("getName");
                    String name = (String) nameMethod.invoke(null);
                    if (name == null) {
                        throw new RuntimeException(clazz.getSimpleName() + " doesn't override a getName() function.");
                    }
                    MachinesPlugin.machineTypes.put(name, clazz);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(clazz.getSimpleName() + " doesn't override a getName() function.");
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
